package com.api.config;

import com.api.security.jwt.JwtTokenUtils;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CaffeineConfig {
	/** Invalid JWT token cache name */
	public static final String INVALID_JWT_CACHE = "invalidJwtTokenCache";

	/** Invalid JWT token caffeine bean name */
	private static final String INVALID_JWT_CAFFEINE_BEAN = "invalidJwtTokenCaffeine";

	/** Utility class of jwt token */
	private final JwtTokenUtils jwtTokenUtils;

	/**
	 * Initialize bean for invalidated jwt token caffeine
	 *
	 * @return jwt token caffeine
	 */
	@Bean(INVALID_JWT_CAFFEINE_BEAN)
	public Caffeine<Object, Object> invalidJwtTokenCaffeine() {
		return Caffeine.newBuilder().recordStats().initialCapacity(100).maximumSize(10).expireAfter(new Expiry<>() {

			@Override
			public long expireAfterCreate(Object jwtToken, Object email, long currentDuration) {
				try {
					return TimeUnit.MILLISECONDS.toNanos(
						jwtTokenUtils.getExpiration(jwtToken.toString()).getTime() - new Date().getTime());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					return TimeUnit.HOURS.toNanos(1);
				}
			}

			@Override
			public long expireAfterUpdate(Object jwtToken, Object email, long currentTime, @NonNegative long currentDuration) {
				return currentDuration;
			}

			@Override
			public long expireAfterRead(Object s, Object l, long currentTime, @NonNegative long currentDuration) {
				return currentDuration;
			}
		});
	}

	/**
	 * Setup Spring's cache manager
	 *
	 * @param jwtCaffeine caffeine of invalidated JWT token
	 * @return caffeineCacheManager
	 */
	@Bean
	public CacheManager cacheManager(@Qualifier(INVALID_JWT_CAFFEINE_BEAN) Caffeine<Object, Object> jwtCaffeine) {
		// config list of caches
		List<CaffeineCache> caffeineCaches = new ArrayList<>();
		caffeineCaches.add(new CaffeineCache(INVALID_JWT_CACHE, jwtCaffeine.build()));

		// setup cache manager
		SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
		caffeineCacheManager.setCaches(caffeineCaches);
		return caffeineCacheManager;
	}
}
