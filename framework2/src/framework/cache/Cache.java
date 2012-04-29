/** 
 * @(#)Cache.java
 */
package framework.cache;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

public class Cache {

	/**
	 * ĳ�ñ���ü
	 */
	public static AbstractCache cache;

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
		} catch (Exception e) {
			cache = EhCache.getInstance();
		}
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
	}

	/**
	 * Ű�� ���� 1��ŭ ������Ų��.
	 * 
	 * @param key Ű
	 * @return ������ �� ��
	 */
	public static long incr(String key) {
		return cache.incr(key, 1);
	}

	/**
	 * Ű�� ���� by ��ŭ ������Ų��.
	 * 
	 * @param key Ű
	 * @param by ������ų ��
	 * @return ������ �� ��
	 */
	public static long incr(String key, int by) {
		return cache.incr(key, by);
	}

	/**
	 * Ű�� ���� 1��ŭ ���ҽ�Ų��.
	 * 
	 * @param key Ű
	 * @return ���ҵ� �� ��
	 */
	public static long decr(String key) {
		return cache.decr(key, 1);
	}

	/**Ű�� ���� by ��ŭ ���ҽ�Ų��.
	 * 
	 * @param key Ű
	 * @param by ���ҽ�ų ��
	 * @return ���ҵ� �� ��
	 */
	public static long decr(String key, int by) {
		return cache.decr(key, by);
	}

	/**
	 * ĳ�ÿ��� Ű�� ���� ���´�.
	 * 
	 * @param key Ű
	 * @return ��
	 */
	public static Object get(String key) {
		return cache.get(key);
	}

	/**
	 * ĳ�ÿ��� Ű�� �迭�� ������ ���´�.
	 * 
	 * @param keys Ű
	 * @return ��
	 */
	public static Map<String, Object> get(String... keys) {
		return cache.get(keys);
	}

	/**
	 * Ű�� ���� ĳ�ÿ��� �����Ѵ�.
	 * 
	 * @param key Ű
	 */
	public static void delete(String key) {
		cache.delete(key);
	}

	/**
	 * ĳ�ø� ��� ����.
	 */
	public static void clear() {
		cache.clear();
	}

	/**
	 * ĳ�ø� �����.
	 */
	public static void stop() {
		cache.stop();
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
}
