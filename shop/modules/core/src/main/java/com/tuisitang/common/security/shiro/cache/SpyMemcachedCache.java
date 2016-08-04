package com.tuisitang.common.security.shiro.cache;

import java.util.Collection;
import java.util.Set;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

/**
 * @author: Genkyo Lee
 * @date: 1/6/13
 */
public class SpyMemcachedCache<K, V> implements Cache<K, V> {
	/**
	 * Private internal logger instance.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SpyMemcachedCache.class);

	/**
	 * The wrapped MemcachedClient instance.
	 */
	private MemcachedClient memcachedClient;

	private int expiration;

	/**
	 * Constructs a new MemcachedCache instance with the given cache.
	 * 
	 * @param memcachedClient
	 *            - delegate Memcached instance this Shiro cache instance will
	 *            wrap.
	 */
	public SpyMemcachedCache(MemcachedClient memcachedClient) {
		if (memcachedClient == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.memcachedClient = memcachedClient;
	}

	public SpyMemcachedCache(MemcachedClient memcachedClient, int expiration) {
		this.memcachedClient = memcachedClient;
		this.expiration = expiration;
	}

	/**
	 * Gets a value of an element which matches the given key.
	 * 
	 * @param key
	 *            the key of the element to return.
	 * @return The value placed into the cache with an earlier put, or null if
	 *         not found or expired
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) throws CacheException {
		try {
			if (key != null) {
				CASValue<Object> objectCASValue = memcachedClient.getAndTouch(
						key.toString(), expiration);
				memcachedClient.get(key.toString());
				if (objectCASValue != null) {
					logger.debug("get object for key {}", key);
					return (V) objectCASValue.getValue();
				} else {
					logger.debug("get null for key {}", key);
					return null;
				}
			} else {
				return null;
			}
		} catch (Throwable throwable) {
			Throwables.propagateIfPossible(throwable, CacheException.class);
			throw Throwables.propagate(throwable);
		}
	}

	/**
	 * Puts an object into the cache.
	 * 
	 * @param key
	 *            the key.
	 * @param value
	 *            the value.
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		try {
			memcachedClient.set(key.toString(), this.expiration, value);
			logger.debug("put {} value for {} seconds", key, this.expiration);
			return value;
		} catch (Throwable throwable) {
			Throwables.propagateIfPossible(throwable, CacheException.class);
			throw Throwables.propagate(throwable);
		}
	}

	/**
	 * Removes the element which matches the key.
	 * <p/>
	 * <p>
	 * If no element matches, nothing is removed and no Exception is thrown.
	 * </p>
	 * 
	 * @param key
	 *            the key of the element to remove
	 */
	@Override
	public V remove(K key) throws CacheException {
		try {
			V previous = get(key);
			if (previous != null) {
				memcachedClient.delete(key.toString());
				logger.debug("delete {} value", key);
			}
			return previous;
		} catch (Throwable throwable) {
			Throwables.propagateIfPossible(throwable, CacheException.class);
			throw Throwables.propagate(throwable);
		}
	}

	/**
	 * Removes all elements in the cache, but leaves the cache in a useable
	 * state.
	 */
	@Override
	public void clear() throws CacheException {
		try {
			memcachedClient.flush();
		} catch (Throwable throwable) {
			Throwables.propagateIfPossible(throwable, CacheException.class);
			throw Throwables.propagate(throwable);
		}
	}

	@Override
	public int size() {
		logger.warn("Spymemcached not support");
		return 0;
	}

	@Override
	public Set<K> keys() {
		logger.warn("Spymemcached not support");
		return null;
	}

	@Override
	public Collection<V> values() {
		logger.warn("Spymemcached not support");
		return null;
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

} 