/** 
 * @(#)Memcached.java
 */
package framework.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import framework.config.Configuration;
import framework.config.ConfigurationException;

/**
 * Memcached ĳ�� ����ü (http://memcached.org/)
 */
public class Memcached extends AbstractCache {

	/**
	 * �̱��� ��ü
	 */
	private static Memcached _uniqueInstance;

	/**
	 * ĳ�� Ŭ���̾�Ʈ
	 */
	private MemcachedClient _client;

	/**
	 * ������, �ܺο��� ��ü�� �ν��Ͻ�ȭ �� �� ������ ����
	 * @throws IOException
	 */
	private Memcached() throws IOException {
		System.setProperty("net.spy.log.LoggerImpl", "net.spy.memcached.compat.log.Log4JLogger");
		List<InetSocketAddress> addrList;
		if (getConfig().containsKey("memcached.host")) {
			addrList = AddrUtil.getAddresses(getConfig().getString("memcached.host"));
		} else if (getConfig().containsKey("memcached.1.host")) {
			int count = 1;
			StringBuilder buffer = new StringBuilder();
			while (getConfig().containsKey("memcached." + count + ".host")) {
				buffer.append(getConfig().getString("memcached." + count + ".host") + " ");
				count++;
			}
			addrList = AddrUtil.getAddresses(buffer.toString());
		} else {
			throw new ConfigurationException("memcached�� ȣ��Ʈ������ �����Ǿ����ϴ�.");
		}
		_client = new MemcachedClient(addrList);
	}

	/** 
	 * ��ü�� �ν��Ͻ��� �������ش�.
	 * 
	 * @return Memcached ��ü�� �ν��Ͻ�
	 * @throws IOException 
	 */
	public static Memcached getInstance() throws IOException {
		if (_uniqueInstance == null) {
			_uniqueInstance = new Memcached();
		}
		return _uniqueInstance;
	}

	@Override
	public void add(String key, Object value, int seconds) {
		_client.add(key, seconds, value);
	}

	@Override
	public void set(String key, Object value, int seconds) {
		_client.set(key, seconds, value);
	}

	@Override
	public void replace(String key, Object value, int seconds) {
		_client.replace(key, seconds, value);
	}

	@Override
	public Object get(String key) {
		Future<Object> future = _client.asyncGet(key);
		try {
			return future.get(1, TimeUnit.SECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return null;
	}

	@Override
	public Map<String, Object> get(String[] keys) {
		Future<Map<String, Object>> future = _client.asyncGetBulk(keys);
		try {
			return future.get(1, TimeUnit.SECONDS);
		} catch (Exception e) {
			future.cancel(false);
		}
		return Collections.<String, Object> emptyMap();
	}

	@Override
	public long incr(String key, int by) {
		return _client.incr(key, by, 0);
	}

	@Override
	public long decr(String key, int by) {
		return _client.decr(key, by, 0);
	}

	@Override
	public void delete(String key) {
		_client.delete(key);
	}

	@Override
	public void clear() {
		_client.flush();
	}

	@Override
	public void stop() {
		_client.shutdown();
	}

	////////////////////////////////////////////////////////////////////////////////////////Private �޼ҵ�

	/**
	 * ��������(config.properties)���� ���� �о���� Ŭ������ �����Ѵ�.
	 * @return ������ü
	 */
	private Configuration getConfig() {
		return Configuration.getInstance();
	}
}
