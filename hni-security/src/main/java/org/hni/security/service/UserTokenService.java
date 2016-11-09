package org.hni.security.service;

import org.hni.security.om.AuthorizedUser;
import org.hni.user.om.User;

public interface UserTokenService {
	String getUserToke(User user, Long organizationId);

	AuthorizedUser getTokenUser(String token);
}
