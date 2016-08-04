package com.tuisitang.common.security.shiro.cache;
import java.util.Map;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;

/**
 * @author: Genkyo Lee
 * @date: 1/5/13
 */

/**
 * Shiro {@code CacheManager} implementation utilizing the Memacached framework for all cache functionality.
 * <p/>
 * This class can {@link #setCacheManager(MemcachedManager) accept} a manually configured
 * {@link MemcachedManager MemcachedManager} instance,
 * or an {@code mamcached.properties} path location can be specified instead and one will be constructed. If neither are
 * specified, Shiro's failsafe mamcached.properties} file will be used by default.
 */
public class SpyMemcachedManager implements CacheManager, Initializable, Destroyable {
	/**
	 * This class's private log instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SpyMemcachedManager.class);

	/**
	 * Indicates if the CacheManager instance was implicitly/automatically
	 * created by this instance, indicating that it should be automatically
	 * cleaned up as well on shutdown.
	 */
	private boolean cacheManagerImplicitlyCreated = false;

	/**
	 * The Memcached cache manager used by this implementation to create cache
	 * client.
	 */
	protected Map<String, MemcachedClient> clients;

	private String servers = "127.0.0.1:11211";

	private String protocol = "TEXT";

	/**
	 * Default no argument constructor
	 */
	public SpyMemcachedManager() {
	}

	/**
	 * Loads an existing Memcached from the cache manager, or starts a new cache
	 * if one is not found.
	 * 
	 * @param name
	 *            the name of the cache to load/create.
	 * @return
	 * @throws org.apache.shiro.cache.CacheException
	 * 
	 */
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("Acquiring cache with name {}", name);

		try {
			MemcachedClient client = clients.get(name);
			if (client == null) {
				if (ConnectionFactoryBuilder.Protocol.BINARY.toString()
						.equalsIgnoreCase(protocol)) {
					client = new MemcachedClient(new BinaryConnectionFactory(),
							AddrUtil.getAddresses(servers));
				} else if (ConnectionFactoryBuilder.Protocol.TEXT.toString()
						.equalsIgnoreCase(protocol)) {
					client = new MemcachedClient(AddrUtil.getAddresses(servers));
				} else {
					throw new CacheException(
							"Please check the memcached client protocol settings, only BINARY and TEXT supported");
				}
				logger.debug(
						"Create cache with name {} to address {} using protocol {}",
						name, servers, protocol);
				clients.put(name, client);
			} else {
				logger.debug("Use existing cache with name {}", name);
			}
//			return new SpyMemcachedCache<K, V>(client, MemcachedObjectType.valueOf(name).getExpiredTime());
			return new SpyMemcachedCache<K, V>(client, MemcachedObjectType.SHIRO_AUTHORIZATION.getExpiredTime());
		} catch (Throwable throwable) {
			Throwables.propagateIfPossible(throwable, CacheException.class);
			throw Throwables.propagate(throwable);
		}
	}

	@Override
	public void destroy() throws Exception {
		if (cacheManagerImplicitlyCreated) {
			for (String name : clients.keySet()) {
				clients.get(name).shutdown();
			}
		}
		cacheManagerImplicitlyCreated = false;
	}

	@Override
	public void init() throws ShiroException {
		// Need to set the LoggerImpl in system environment for spymemcached
		System.setProperty("net.spy.log.LoggerImpl",
				"net.spy.memcached.compat.log.Log4JLogger");

		if (clients == null || clients.size() == 0) {
			clients = Maps.newConcurrentMap();
			logger.debug("Implicit cache clients successfully");
			cacheManagerImplicitlyCreated = true;
		}
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
} 