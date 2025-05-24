package com.api.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MailCommonService {
	private final JavaMailSender mailSender;

	@Async
	public void send(String to, String subject, String templateHtml) throws Exception {
		log.info("[MailCommonService.send] begin send mail");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		log.info("[MailCommonService.send] send mail to: {}", to);
		helper.setTo(to);
		helper.setSubject(subject);
		message.setContent(templateHtml, "text/html; charset=UTF-8");

		mailSender.send(message);
		log.info("[MailCommonService.send] send mail success");
	}

	public String applyMailTemplate(String mailTemplate, Map<String, String> params) {
		StringBuilder mailBuilder = new StringBuilder();
		int startIndex = 0;
		int endIndex;

		// while there is still param template "{param}", extract the "param" as key and get its value from the map
		while ((endIndex = mailTemplate.indexOf("{", startIndex)) != -1) {
			mailBuilder.append(mailTemplate, startIndex, endIndex);
			startIndex = endIndex + 1;
			endIndex = mailTemplate.indexOf("}", startIndex);
			if (endIndex != -1) {
				String key = mailTemplate.substring(startIndex, endIndex);
				if (!params.containsKey(key)) {
					throw new IllegalArgumentException(
							"The mail template parameter [" + key + "] is not provided in the map param");
				}
				mailBuilder.append(params.get(key));
				startIndex = endIndex + 1;
			} else {
				break;
			}
		}
		mailBuilder.append(mailTemplate.substring(startIndex));
		return mailBuilder.toString();
	}
}
