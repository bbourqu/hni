package org.hni.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.log4j.BasicConfigurator;
import org.hni.security.om.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@Transactional
public class TestPermissionService {

	@Inject
	private PermissionService permissionService;

	public TestPermissionService() {
		BasicConfigurator.configure();
	}

	@Test
	public void testGet() {
		Permission permission = permissionService.get(11L);
		assertEquals("organizations", permission.getDomain());
		assertEquals("create", permission.getValue());
		//assertNull(permission.getInstance());
	}

	@Test
	public void testUpdate() {
		Permission permission = permissionService.get(1L);
		String updatedName = "blah";
		permission.setDomain(updatedName);
		permissionService.update(permission);
		Permission updatedPermission = permissionService.get(1L);
		assertEquals(updatedName, updatedPermission.getDomain());
	}

	@Test
	public void testDelete() {
		Permission permission = permissionService.get(1L);
		permissionService.delete(permission);
		Permission shouldBeNullPermission = permissionService.get(1L);
		assertNull(shouldBeNullPermission);
	}

	@Test
	public void testInsert() {
		Permission permission = new Permission();
		String insertedName = "ad hoc domain";
		String insertPermission = "create";
		permission.setDomain(insertedName);
		permission.setValue(insertPermission);
		Permission insertedPermission = permissionService.insert(permission);
		assertEquals(insertedName, insertedPermission.getDomain());
		assertEquals(insertPermission, insertedPermission.getValue());
		assertTrue(insertedPermission.getId() > 64);
	}
}