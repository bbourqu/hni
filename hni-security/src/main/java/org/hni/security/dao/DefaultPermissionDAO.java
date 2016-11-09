package org.hni.security.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.security.om.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultPermissionDAO extends AbstractDAO<Permission> implements PermissionDAO {
	private static final Logger logger = LoggerFactory.getLogger(PermissionDAO.class);

	public DefaultPermissionDAO() {
		super(Permission.class);
	}
}