package org.hni.user.service;

import java.util.List;

import javax.inject.Inject;

import org.hni.common.service.AbstractService;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserService extends AbstractService<User> implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private UserDAO userDao;

	@Inject
	public DefaultUserService(UserDAO userDao) {
		super(userDao);
		this.userDao = userDao;
	}

	@Override
	public List<User> byMobilePhone(String byMobilePhone) {
		return userDao.byMobilePhone(byMobilePhone);
	}

	@Override
	public List<User> byLastName(String lastName) {
		return userDao.byLastName(lastName);
	}

	@Override
	public User byEmailAddress(String emailAddress) {
		return userDao.byEmailAddress(emailAddress);
	}

}
