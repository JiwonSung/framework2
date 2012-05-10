/*
 * @(#)CryptoUtil.java
 */
package framework.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * ��/��ȣȭ ���� ����� �ϴ� ��ƿ��Ƽ Ŭ�����̴�.
 */
public class CryptUtil {

	/**
	 * ������, �ܺο��� ��ü�� �ν��Ͻ�ȭ �� �� ������ ����
	 */
	private CryptUtil() {
	}

	/**
	 * �޽����� MD5 �˰������� �ؽ��Ѵ�.
	 * 
	 * @param message �����޽���
	 * @return �ؽ��� ���ڿ�
	 */
	public static String hashMD5(String message) {
		return hash(message, "MD5");
	}

	/**
	 * �޽����� SHA-1 �˰������� �ؽ��Ѵ�.
	 * 
	 * @param message �����޽���
	 * @return �ؽ��� ���ڿ�
	 */
	public static String hashSHA1(String message) {
		return hash(message, "SHA-1");
	}

	/**
	 * �޽����� SHA-256 �˰������� �ؽ��Ѵ�.
	 * 
	 * @param message �����޽���
	 * @return �ؽ��� ���ڿ�
	 */
	public static String hashSHA256(String message) {
		return hash(message, "SHA-256");
	}

	/**
	 * �޽����� SHA-512 �˰������� �ؽ��Ѵ�.
	 * 
	 * @param message �����޽���
	 * @return �ؽ��� ���ڿ�
	 */
	public static String hashSHA512(String message) {
		return hash(message, "SHA-512");
	}

	/**
	 * �޽����� BASE64 �˰������� ���ڵ��Ѵ�.
	 * 
	 * @param message �����޽���
	 * @return ���ڵ��� ���ڿ�
	 */
	public static String encodeBase64(String message) {
		return new String(Base64.encodeBase64(message.getBytes()));
	}

	/**
	 * �޽����� BASE64 �˰������� ���ڵ��Ѵ�.
	 * 
	 * @param message ���� �޽���
	 * @return ���ڵ��� ���ڿ�
	 */
	public static String decodeBase64(String message) {
		return new String(Base64.decodeBase64(message.getBytes()));
	}

	/**
	 * �޽����� ����Ű�� �̿��Ͽ� AES �˰������� ��ȣȭ�Ѵ�.
	 * 
	 * @param message �����޽���
	 * @param privateKey ����Ű 
	 * @return ��ȣȭ�� ���ڿ�
	 */
	public static String encryptAES(String message, String privateKey) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(privateKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return String.valueOf(Hex.encodeHex(cipher.doFinal(message.getBytes()))).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �޽����� ����Ű�� �̿��Ͽ� AES �˰������� ��ȣȭ�Ѵ�.
	 *  
	 * @param message �����޽���
	 * @param privateKey ����Ű 
	 * @return ��ȣȭ�� ���ڿ�
	 */
	public static String decryptAES(String message, String privateKey) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(privateKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(Hex.decodeHex(message.toCharArray())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �޽����� ����Ű�� �̿��Ͽ� DES �˰������� ��ȣȭ�Ѵ�.
	 * 
	 * @param message �����޽���
	 * @param privateKey ����Ű 
	 * @return ��ȣȭ�� ���ڿ�
	 */
	public static String encryptDES(String message, String privateKey) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(privateKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return String.valueOf(Hex.encodeHex(cipher.doFinal(message.getBytes()))).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �޽����� ����Ű�� �̿��Ͽ� DES �˰������� ��ȣȭ�Ѵ�.
	 *  
	 * @param message �����޽���
	 * @param privateKey ����Ű 
	 * @return ��ȣȭ�� ���ڿ�
	 */
	public static String decryptDES(String message, String privateKey) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(privateKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(Hex.decodeHex(message.toCharArray())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////Private �޼ҵ�

	/**
	 * �޽����� �־��� �˰������� �ؽ��Ѵ�.
	 * @param message �����޽���
	 * @param algorithm �ؽ� �˰���
	 * @return �ؽ��� ���ڿ�
	 */
	private static String hash(String message, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return new String(Hex.encodeHex(md.digest(message.getBytes()))).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
