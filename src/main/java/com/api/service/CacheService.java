package com.api.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CacheService {
	/** Spring cache manager */
	private final CacheManager cacheManager;

	/**
	 * Register/update cache value
	 *
	 * @param cacheName cache name
	 * @param key       key
	 * @param value     value
	 */
	public void cachePut(@NonNull String cacheName, @NonNull Object key, @NonNull Object value) {
		this.getCache(cacheName).put(key, value);
	}

	/**
	 * Retrieve cache value with the Object class as the default type
	 *
	 * @param cacheName cache name
	 * @param key       key
	 */
	public Object cacheGet(@NonNull String cacheName, @NonNull Object key) {
		return this.cacheGet(cacheName, key, Object.class);
	}

	/**
	 * Retrieve cache value with the defined class
	 *
	 * @param cacheName cache name
	 * @param key       key
	 * @param clazz     defined class for value
	 * @param <T>       generic type of value
	 * @return cache value by key
	 */
	public <T> T cacheGet(@NonNull String cacheName, @NonNull Object key, @NonNull Class<T> clazz) {
		return this.getCache(cacheName).get(key, clazz);
	}

	/**
	 * retrieve cache by name
	 *
	 * @param cacheName cache name
	 * @return cache
	 */
	private Cache getCache(@NonNull String cacheName) {
		return Optional.ofNullable(cacheManager.getCache(cacheName))
			.orElseThrow(() -> new IllegalArgumentException("Invalid cache name"));
	}
}
