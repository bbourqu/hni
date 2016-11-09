package org.hni.user.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDAO extends AbstractDAO<User> implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public DefaultUserDAO() {
		super(User.class);
	}

	@Override
	public List<User> byMobilePhone(String mobilePhone) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.mobilePhone = :mobilePhone").setParameter("mobilePhone", mobilePhone);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<User> byLastName(String lastName) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.lastName = :lastName").setParameter("lastName", lastName);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public User byEmailAddress(String email) {
		try {
			Query q = em.createQuery("SELECT x FROM User x WHERE x.email = :email").setParameter("email", email);
			return (User) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
