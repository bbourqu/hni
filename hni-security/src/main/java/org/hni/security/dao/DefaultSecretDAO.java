package org.hni.security.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.security.om.Secret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultSecretDAO extends AbstractDAO<Secret> implements SecretDAO {
	private static final Logger logger = LoggerFactory.getLogger(SecretDAO.class);

	public DefaultSecretDAO() {
		super(Secret.class);
	}
}