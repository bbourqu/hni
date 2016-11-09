package org.hni.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.security.om.Encryption;
import org.hni.security.om.OrganizationUserPermission;
import org.hni.user.om.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@Transactional
public class TestUserSecurityService {

	@Inject
	private UserSecurityService userSecurityService;

	public TestUserSecurityService() {
		BasicConfigurator.configure();
	}

	//@Test
	public void testAuthenticateUserByEmailAddress() {
		User user = new User();
		user.setHashedSecret("pwd");
		user.setEmail("superuser@hni.com");
		user.setOrganizationId(2L);
		User tokenUser = userSecurityService.authenticate(user);
		assertTrue(null != tokenUser.getToken());
		assertTrue(!tokenUser.getToken().isEmpty());
	}

	//@Test
	public void testAuthorizeUser() {
		String token = getUserToken();
		Set<OrganizationUserPermission> orgUserPermissions = userSecurityService.authorize(token);
		assertTrue(null != orgUserPermissions);
		assertTrue(!orgUserPermissions.isEmpty());
		assertTrue(78 == orgUserPermissions.size());
		boolean createUserPermissionFound = false;
		for (OrganizationUserPermission orgUserPermission : orgUserPermissions) {
			if ("organizations".equals(orgUserPermission.getPermissionDomain()) && "*".equals(orgUserPermission.getPermission())
					&& 2 == orgUserPermission.getOrganizationId() && 1 == orgUserPermission.getUserId()) {
				createUserPermissionFound = true;
			}
		}
		assertTrue(createUserPermissionFound);
	}

	//@Test
	public void testValidateToken() {
		String tokenString = getUserToken();
		User tokenUser = userSecurityService.validateToken(tokenString);
		assertTrue(1L == tokenUser.getId());
	}

	@Test
	public void testEncryptionDecryption() {
		String key = Encryption.generateKey();
		System.out.println("Key: " + key);
		Encryption encryption = new Encryption(key);
		String testData = "test data";
		String encrypted = encryption.encrypt(testData);
		String decrypted = encryption.decrypt(encrypted);
		assertEquals(testData, decrypted);
	}

	private String getUserToken() {
		User user = new User();
		user.setHashedSecret("pwd");
		user.setEmail("superuser@hni.com");
		user.setOrganizationId(2L);
		User tokenUser = userSecurityService.authenticate(user);
		return tokenUser.getToken();
	}
}