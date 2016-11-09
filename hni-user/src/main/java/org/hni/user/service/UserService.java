package org.hni.user.service;

import java.util.List;

import org.hni.common.service.BaseService;
import org.hni.user.om.User;

public interface UserService extends BaseService<User> {

	List<User> byMobilePhone(String byMobilePhone);

	List<User> byLastName(String lastName);

	User byEmailAddress(String emailAddress);
}
