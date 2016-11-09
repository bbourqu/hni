package org.hni.user.dao;

import java.util.List;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.User;

public interface UserDAO extends BaseDAO<User> {

	List<User> byMobilePhone(String byMobilePhone);

	List<User> byLastName(String lastName);

	User byEmailAddress(String emailAddress);
}
