package com.api.service;

import com.api.common.CommonConstants;
import com.api.common.MailTemplates;
import com.api.config.CaffeineConfig;
import com.api.database.entity.ForgotPasswordEntity;
import com.api.database.entity.UserAccountEntity;
import com.api.database.repository.ForgotPasswordRepository;
import com.api.database.repository.UserAccountRepository;
import com.api.dto.request.LoginRequestDto;
import com.api.dto.request.ResetPasswordRequestDto;
import com.api.dto.response.ApiResponse;
import com.api.enums.ForgotPasswordStatusEnum;
import com.api.enums.InternalErrorEnum;
import com.api.enums.ResetPasswordRefEnum;
import com.api.exception.InternalServerException;
import com.api.exception.InvalidPasswordException;
import com.api.exception.RecordNotFoundException;
import com.api.security.jwt.JwtTokenUtils;
import com.api.security.userdetails.UserDetailsImpl;
import com.api.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	/** JPA repository of UserAccountEntity */
	private final UserAccountRepository userAccountRepository;

	/** JPA repository of ForgotPasswordEntity */
	private final ForgotPasswordRepository forgotPasswordRepository;

	/** PasswordEncoder */
	private final PasswordEncoder passwordEncoder;

	/** Utility class of jwt token */
	private final JwtTokenUtils jwtTokenUtils;

	/** Utility common service for email processing **/
	private final MailCommonService mailCommonService;

	/** Utility class for cache management */
	private final CacheService cacheService;

	/** Current request's user info service */
	private final RequestInfoService requestInfoService;

	/**
	 * Process the login request from user
	 *
	 * @param loginRequestDto dto contains user's login request info
	 * @return ApiResponse with token
	 */
	public ApiResponse login(LoginRequestDto loginRequestDto, String bearerToken) {
		final String username = loginRequestDto.getUsername();

		// fetch user
		UserAccountEntity userAccount = userAccountRepository
			.findByUsernameOrEmailAndDeleteFlagFalse(username, username)
			.orElseThrow(() -> new InvalidPasswordException(""));

		// valid password match
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), userAccount.getPassword())) {
			throw new InvalidPasswordException("");
		}

		// if user has already had a token, marks it as invalidate
		if (ValidationUtils.nonNullOrEmpty(bearerToken)) {
			cacheService.cachePut(CaffeineConfig.INVALID_JWT_CACHE,
				bearerToken.substring(CommonConstants.BEARER_PREFIX.length()), userAccount.getEmail());
		}

		// generate token using the username and the built user detail
		return ApiResponse.apiResponseSuccess(
			jwtTokenUtils.generateJwtToken(username, UserDetailsImpl.build(userAccount)));
	}

	/**
	 * Initialize user's change password request via email
	 *
	 * @param username user's account username/email
	 * @param data     additional data from client that need to be attached to the link change
	 * @param origin   request origin get from header
	 * @return ApiResponse 200/404
	 */
	public ApiResponse initResetPassword(String username, String data, String origin) {
		// get user info by username
		userAccountRepository
			.findByUsernameOrEmailAndDeleteFlagFalse(username, username).ifPresentOrElse(userAccount -> {
				// password change requests are rate limited to one request per 30 seconds
				forgotPasswordRepository.findFirstByUserAccountIdAndAndStatusAndDeleteFlagFalseOrderByCreatedAtDesc(
					userAccount.getId(), ForgotPasswordStatusEnum.ACTIVE.value()).ifPresent(oldRequest -> {
					if (LocalDateTime.now().plusSeconds(-30).isBefore(oldRequest.getCreatedAt())) {
						throw new IllegalArgumentException("Try again after 30 seconds");
					}
				});

				// generate client reset password link
				String linkChangePassword = this.generateLinkChangePassword(userAccount, data, origin);

				// apply change password mail template
				String mailContent = mailCommonService.applyMailTemplate(MailTemplates.CHANGE_PWD,
					Map.of("user", userAccount.getUsername(), "linkChangePwd", linkChangePassword));

				// send mail in async
				try {
					mailCommonService.send(userAccount.getEmail(), "Reset password", mailContent);
				} catch (Exception e) {
					throw new MailSendException(e.getMessage());
				}
			}, () -> {
				throw new RecordNotFoundException(username);
			});
		return ApiResponse.apiResponseSuccess();
	}

	/**
	 * Generate UUID token and concatenate with the origin for the link change password.
	 * Save all the relevant information to the forgot_password table
	 *
	 * @param user   user account info
	 * @param data   additional data from client that need to be attached to the link change
	 * @param origin current request origin
	 * @return the generated link change password
	 */
	private String generateLinkChangePassword(UserAccountEntity user, String data, String origin) {
		log.info("[AuthService.generateLinkChangePassword] begin generateLinkChangePassword");

		ForgotPasswordEntity forgotPassword = new ForgotPasswordEntity();
		forgotPassword.setUserAccountId(user.getId());
		forgotPassword.setEmail(user.getEmail());
		forgotPassword.setOrigin(origin);
		forgotPassword.setToken(passwordEncoder.encode(UUID.randomUUID().toString()));
		forgotPassword.setExpiredDate(LocalDateTime.now().plusMinutes(5));
		forgotPassword.setStatus(ForgotPasswordStatusEnum.ACTIVE.value());
		forgotPassword.setCommonRegist(user.getId());

		forgotPasswordRepository.save(forgotPassword);

		log.info("[AuthService.generateLinkChangePassword] end generateLinkChangePassword");


		return String.format("%s/password-recovery?rs_ref=%s&preserve=%s&token=%s", origin,
			ResetPasswordRefEnum.RESET_PASSWORD.value(),
			UriUtils.encode(data, StandardCharsets.UTF_8),
			UriUtils.encode(forgotPassword.getToken(), StandardCharsets.UTF_8));
	}

	/**
	 * Validate if the forgot password token status is active, or still not yet expired
	 *
	 * @param token forgot password token
	 * @return api response contains token
	 * @throws InternalServerException throw the RESET_TOKEN_EXPIRED(1004) or RESET_TOKEN_NOT_FOUND(1003)
	 */
	public ApiResponse validateResetToken(String token) {
		forgotPasswordRepository.findByTokenAndStatusAndDeleteFlagFalse(token, ForgotPasswordStatusEnum.ACTIVE.value())
			.ifPresentOrElse(forgotPassword -> {
				if (!forgotPassword.getExpiredDate().isAfter(LocalDateTime.now())) {
					throw new InternalServerException(InternalErrorEnum.RESET_TOKEN_EXPIRED);
				}
			}, () -> {
				throw new InternalServerException(InternalErrorEnum.RESET_TOKEN_NOT_FOUND);
			});
		return ApiResponse.apiResponseSuccess(token);
	}

	/**
	 * reset password of user based on reset password dto
	 *
	 * @param requestDto reset password dto, with old, new password and the forgot password token for validating
	 * @return api response success
	 */
	public ApiResponse resetPassword(ResetPasswordRequestDto requestDto) {
		// find the user's forgot password request by the reset token
		forgotPasswordRepository.findByTokenAndStatusAndDeleteFlagFalse(requestDto.getResetToken(),
				ForgotPasswordStatusEnum.ACTIVE.value())
			.ifPresentOrElse(forgotPassword -> {
				// validate new password must be equal to the confirm password
				if (!requestDto.getNewPassword().equals(requestDto.getConfirmNewPassword())) {
					throw new InternalServerException(InternalErrorEnum.CONFIRM_PASSWORD_NOT_MATCH);
				}
				// validate if the token has already expired
				if (!forgotPassword.getExpiredDate().isAfter(LocalDateTime.now())) {
					throw new InternalServerException(InternalErrorEnum.RESET_TOKEN_EXPIRED);
				}
				// get user account record to update the new password
				userAccountRepository.findByIdAndDeleteFlagFalse(forgotPassword.getUserAccountId())
					.ifPresentOrElse(userAccount -> {
						userAccount.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
						userAccount.setCommonUpdate(userAccount.getId());
						userAccountRepository.save(userAccount);
					}, () -> {
						throw new RecordNotFoundException("user");
					});

				// set the forgot password request's status
				forgotPassword.setStatus(ForgotPasswordStatusEnum.DEACTIVE.value());
				forgotPassword.setCommonUpdate(forgotPassword.getUserAccountId());
				forgotPasswordRepository.save(forgotPassword);
			}, () -> {
				throw new InternalServerException(InternalErrorEnum.RESET_TOKEN_NOT_FOUND);
			});

		return ApiResponse.apiResponseSuccess();
	}

	/**
	 * Process the logout request from user, on logout, mark the jwt token as invalid in the caffeine cache
	 *
	 * @param bearerToken current header's token
	 * @return api response success
	 * @throws RecordNotFoundException throw when there is no user account can be founded with the SCH userId
	 * @apiNote SCH SecurityContextHolder
	 */
	public ApiResponse logout(String bearerToken) {
		final String jwtToken = bearerToken.replace(CommonConstants.BEARER_PREFIX, "");
		userAccountRepository.findByIdAndDeleteFlagFalse(requestInfoService.getUserId())
			.ifPresentOrElse(userAccount ->
					cacheService.cachePut(CaffeineConfig.INVALID_JWT_CACHE, jwtToken, userAccount.getEmail()),
				RecordNotFoundException::new);
		return ApiResponse.apiResponseSuccess();
	}
}
