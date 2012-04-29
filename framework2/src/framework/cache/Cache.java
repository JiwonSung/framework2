/** 
 * @(#)Cache.java
 */
package framework.cache;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Cache {

	/**
	 * �ΰŰ�ü ����
	 */
	private static Log _logger = LogFactory.getLog(framework.cache.Cache.class);

	/**
	 * ĳ�ñ���ü
	 */
	public static AbstractCache cache;

	/**
	 * ĳ�ñ���ü �̸�
	 */
	public static String cacheName;

	/**
	 * �⺻ ĳ�� �ð� (30��)
	 */
	private final static int DEFAULT_DURATION = 60 * 60 * 24 * 30;

	/**
	 * ������, �ܺο��� ��ü�� �ν��Ͻ�ȭ �� �� ������ ���� 
	 */
	private Cache() {
	}

	/**
	 * ĳ�� �ʱ�ȭ, ���������� �о� ĳ�� ����ü�� �����Ѵ�.
	 */
	public static void init() {
		try {
			cache = Memcached.getInstance();
			cacheName = "Memcached";
		} catch (Exception e) {
			cache = EhCache.getInstance();
			cacheName = "EhCache";
		}
		getLogger().info(String.format("[ %s ] init : �ʱ�ȭ ����", cacheName));
	}

	/**
	 * Ű�� ���� ĳ�ÿ� �߰��Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 */
	public static void add(String key, Object value) {
		isSerializable(value);
		cache.add(key, value, DEFAULT_DURATION);
		getLogger().debug(String.format("[ %s ] add : { key=%s, value=%s, seconds=%d }", cacheName, key, value, DEFAULT_DURATION));
	}

	/**
	 * Ű�� ���� ĳ�ÿ� �߰��Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 * @param seconds ĳ�ýð�(�ʴ���)
	 */
	public static void add(String key, Object value, int seconds) {
		isSerializable(value);
		cache.add(key, value, seconds);
		getLogger().debug(String.format("[ %s ] add : { key=%s, value=%s, seconds=%d }", cacheName, key, value, seconds));
	}

	/**
	 * Ű�� ���� ĳ�ÿ� �����Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 */
	public static void set(String key, Object value) {
		isSerializable(value);
		cache.set(key, value, DEFAULT_DURATION);
		getLogger().debug(String.format("[ %s ] set : { key=%s, value=%s, seconds=%d }", cacheName, key, value, DEFAULT_DURATION));
	}

	/**
	 * Ű�� ���� ĳ�ÿ� �����Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 * @param seconds ĳ�ýð�(�ʴ���)
	 */
	public static void set(String key, Object value, int seconds) {
		isSerializable(value);
		cache.set(key, value, seconds);
		getLogger().debug(String.format("[ %s ] set : { key=%s, value=%s, seconds=%d }", cacheName, key, value, seconds));
	}

	/**
	 * Ű�� ���� ĳ�ÿ��� ��ü�Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 */
	public static void replace(String key, Object value) {
		isSerializable(value);
		cache.replace(key, value, DEFAULT_DURATION);
		getLogger().debug(String.format("[ %s ] replace : { key=%s, value=%s, seconds=%d }", cacheName, key, value, DEFAULT_DURATION));
	}

	/**
	 * Ű�� ���� ĳ�ÿ��� ��ü�Ѵ�.
	 * 
	 * @param key Ű
	 * @param value ��
	 * @param seconds ĳ�ýð�(�ʴ���)
	 */
	public static void replace(String key, Object value, int seconds) {
		isSerializable(value);
		cache.replace(key, value, seconds);
		getLogger().debug(String.format("[ %s ] replace : { key=%s, value=%s, seconds=%d }", cacheName, key, value, seconds));
	}

	/**
	 * Ű�� ���� 1��ŭ ������Ų��.
	 * 
	 * @param key Ű
	 * @return ������ �� ��
	 */
	public static long incr(String key) {
		long result = cache.incr(key, 1);
		getLogger().debug(String.format("[ %s ] incr : { key=%s, by=%d }", cacheName, key, 1));
		return result;
	}

	/**
	 * Ű�� ���� by ��ŭ ������Ų��.
	 * 
	 * @param key Ű
	 * @param by ������ų ��
	 * @return ������ �� ��
	 */
	public static long incr(String key, int by) {
		long result = cache.incr(key, by);
		getLogger().debug(String.format("[ %s ] incr : { key=%s, by=%d }", cacheName, key, by));
		return result;
	}

	/**
	 * Ű�� ���� 1��ŭ ���ҽ�Ų��.
	 * 
	 * @param key Ű
	 * @return ���ҵ� �� ��
	 */
	public static long decr(String key) {
		long result = cache.decr(key, 1);
		getLogger().debug(String.format("[ %s ] decr : { key=%s, by=%d }", cacheName, key, 1));
		return result;
	}

	/**Ű�� ���� by ��ŭ ���ҽ�Ų��.
	 * 
	 * @param key Ű
	 * @param by ���ҽ�ų ��
	 * @return ���ҵ� �� ��
	 */
	public static long decr(String key, int by) {
		long result = cache.decr(key, by);
		getLogger().debug(String.format("[ %s ] decr : { key=%s, by=%d }", cacheName, key, by));
		return result;
	}

	/**
	 * ĳ�ÿ��� Ű�� ���� ���´�.
	 * 
	 * @param key Ű
	 * @return ��
	 */
	public static Object get(String key) {
		Object value = cache.get(key);
		getLogger().debug(String.format("[ %s ] get : { key=%s, value=%s }", cacheName, key, value));
		return value;
	}

	/**
	 * ĳ�ÿ��� Ű�� �迭�� ������ ���´�.
	 * 
	 * @param keys Ű
	 * @return ��
	 */
	public static Map<String, Object> get(String... keys) {
		Map<String, Object> valueMap = cache.get(keys);
		getLogger().debug(String.format("[ %s ] get : { key=%s, value=%s }", cacheName, keys, valueMap));
		return valueMap;
	}

	/**
	 * Ű�� ���� ĳ�ÿ��� �����Ѵ�.
	 * 
	 * @param key Ű
	 */
	public static void delete(String key) {
		cache.delete(key);
		getLogger().debug(String.format("[ %s ] delete : { key=%s }", cacheName, key));
	}

	/**
	 * ĳ�ø� ��� ����.
	 */
	public static void clear() {
		cache.clear();
		getLogger().debug(String.format("[ %s ] clear : ĳ�� Ŭ���� ����", cacheName));
	}

	/**
	 * ĳ�ø� �����.
	 */
	public static void stop() {
		cache.stop();
		getLogger().debug(String.format("[ %s ] stop : ĳ�� ���� ����", cacheName));
	}

	//////////////////////////////////////////////////////////////////////////////////////////Private �޼ҵ�

	/**
	 * ����ȭ ���� ��ü���� �Ǻ��Ѵ�.
	 * @param value
	 */
	private static void isSerializable(Object value) {
		if (value != null && !(value instanceof Serializable)) {
			throw new CacheException(new NotSerializableException(value.getClass().getName()));
		}
	}

	private static Log getLogger() {
		return Cache._logger;
	}
}
