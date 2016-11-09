package org.hni.security.om;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Encryption {
	private static final String ALGORITHM = "AES";
	private static final String UTF8_DASH = "UTF-8";
	private static final String UTF8 = "UTF8";
	private static final Log LOG = LogFactory.getLog(Encryption.class);
	private static SecretKey secretKey = null;

	public Encryption(String key) {
		try {
			String internalKey = new String(Base64.decodeBase64(key.getBytes()), UTF8_DASH);
			secretKey = new SecretKeySpec(Base64.decodeBase64(internalKey.getBytes()), ALGORITHM);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static final String generateKey() {
		String response = "";
		try {
			SecretKey secret = KeyGenerator.getInstance(ALGORITHM).generateKey();
			response = new String(Base64.encodeBase64(new String(Base64.encodeBase64(secret.getEncoded()), UTF8_DASH).getBytes()),
					UTF8_DASH);
		} catch (Exception e) {
			LOG.warn(e, e);
		}
		return response;
	}

	/**
	 * Quick encryption method, so the password isn't in plaintext
	 * 
	 * @param plainText
	 * @return String
	 */
	public String encrypt(String plainText) {
		String encryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] utf8 = plainText.getBytes(UTF8);
			byte[] encrypted = cipher.doFinal(utf8);
			encrypted = Base64.encodeBase64(encrypted);
			encryptedValue = new String(encrypted, UTF8_DASH);
		} catch (Exception e) {
			LOG.warn(e, e);
		}
		return encryptedValue;
	}

	/**
	 * decryptor to decipher encrypted password.
	 * 
	 * @param encryptedString
	 * @return String
	 */
	public String decrypt(String encryptedString) {
		String decryptedValue = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] utf8 = Base64.decodeBase64(encryptedString.getBytes());
			byte[] decrypted = cipher.doFinal(utf8);
			decryptedValue = new String(decrypted, UTF8_DASH);
		} catch (Exception e) {
			LOG.warn(e, e);
		}
		return decryptedValue;
	}
}
