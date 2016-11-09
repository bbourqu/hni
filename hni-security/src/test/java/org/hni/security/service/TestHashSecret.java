package org.hni.security.service;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class TestHashSecret {

	private RandomNumberGenerator rnd = new SecureRandomNumberGenerator();
	
	@Test
	public void getHash() {
		HashedPasswordAndSalt hps = hashPassword("test123");
		System.out.println(hps.hashedPasswordBase64);
		System.out.println(hps.salt);
	}
	
	private HashedPasswordAndSalt hashPassword(String password)
	{
		if ( null == password ) {
			return null; // cannot hash empty passwords
		}
		ByteSource salt = rnd.nextBytes();

		//Now hash the plain-text password with the random salt and multiple
		//iterations and then Base64-encode the value (requires less space than Hex):
		String hashedPasswordBase64 = new Sha256Hash(password, salt, 1024).toBase64();

		return new HashedPasswordAndSalt(hashedPasswordBase64, salt.toBase64());
	}
	
	private class HashedPasswordAndSalt {
		public String hashedPasswordBase64;
		public String salt;
		public HashedPasswordAndSalt(String pass, String salt)
		{
			this.hashedPasswordBase64 = pass;
			this.salt = salt;
		}
		
	}
	
}
