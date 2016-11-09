package org.hni.security.service.security;

import java.util.List;

import javax.inject.Inject;

import org.hni.organization.service.OrganizationUserService;
import org.hni.security.dao.SecretDAO;
import org.hni.security.service.DefaultUserTokenService;
import org.hni.security.service.RolePermissionService;
import org.hni.user.om.User;
import org.springframework.stereotype.Component;

@Component
public class EmailAuthenticator implements Authenticator {

	private OrganizationUserService organizationUserService;
	private SecretDAO secretDAO;
	private RolePermissionService rolePermissionService;

	@Inject
	public EmailAuthenticator(OrganizationUserService organizationUserService, SecretDAO secretDAO,
			RolePermissionService rolePermissionService) {
		this.organizationUserService = organizationUserService;
		this.secretDAO = secretDAO;
		this.rolePermissionService = rolePermissionService;
	}

	@Override
	public String authenticate(String emailAddress, String password, Long organizationId) {

		String tokenValue = "";
		/*
		List<User> users = organizationUserService.byEmailAddress(emailAddress);
		User user = parseSingleUserFromList(users);
		if (validateUserPassword(user, password)) {
			tokenValue = generateSessionToken(user, organizationId);
		}
		*/
		return tokenValue;
	}

	private String generateSessionToken(User user, Long organizationId) {
		DefaultUserTokenService userToken = new DefaultUserTokenService(secretDAO, rolePermissionService, organizationUserService);
		String token = userToken.getUserToke(user, organizationId);
		return token;
	}
}