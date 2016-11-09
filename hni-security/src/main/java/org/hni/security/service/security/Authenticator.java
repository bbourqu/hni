package org.hni.security.service.security;

import java.util.List;

import org.hni.user.om.User;

/**
 * allow different ways of authentication
 *
 */
public interface Authenticator {

	/**
	 * authenticate user based on identifier (could be phone, email, or
	 * something else)
	 * 
	 * TODO: known no salt.
	 * 
	 * TODO: will probably fail if no user with that userid
	 * 
	 * @param identifier
	 * @param password
	 * @return String token
	 */
	String authenticate(String identifier, String password, Long organizationId);

	/**
	 * parses a single user from what's supposed to be a list of 1 user.
	 * 
	 * @param users
	 * @return User
	 */
	default User parseSingleUserFromList(List<User> users) {
		User user = new User();
		if (users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}

	/**
	 * validate whether the user password matches.
	 * 
	 * @param user
	 * @param password
	 * @return boolean
	 */
	default boolean validateUserPassword(User user, String password) {
		boolean valid = false;
		if (!user.getHashedSecret().isEmpty()) {
			if (password.equals(user.getHashedSecret())) {
				valid = true;
			}
		}
		return valid;
	}
}