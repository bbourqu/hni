package org.hni.security.realm;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(PasswordRealm.class);
	public static final String REALM_NAME = "passwordRealm";
	
	// DO NOT inject *Service layers here.  This entire class is NOT transactional because it's instantiated before Spring transactions
	// are up and injecting the *Service classes here will cause them to become non-transactional as well.
	
	@Inject protected UserDAO userDao;
	
	public PasswordRealm() {
		setAuthenticationTokenClass(UsernamePasswordToken.class);
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) 
	{
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("Performing authorization for user "+principals.getPrimaryPrincipal());
		
		// TODO: perform processing to determine roles and permissions.  This is non-trivial!
		Set<String> roles = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();
		
		// Tell Shiro about the user's Roles and Permissions
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo(roles);
		authInfo.addStringPermissions(permissions);
		return authInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("Attempting authentication of user "+token.getPrincipal());
		User user = null;
		
		try {
			user = userDao.byEmailAddress((String)token.getPrincipal());	
			if (null == user ) {
				logger.warn("Could not find User for principal:"+(String)token.getPrincipal());
				return new SimpleAuthenticationInfo("","", new SimpleByteSource(REALM_NAME.getBytes()), REALM_NAME);				
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new SimpleAuthenticationInfo("","", new SimpleByteSource(REALM_NAME.getBytes()), REALM_NAME);
		}
		ByteSource salt = new SimpleByteSource(Base64.decodeBase64(user.getSalt()));
		logger.info("Auth info = "+user.getEmail()+" - "+user.getHashedSecret());
		
		return new SimpleAuthenticationInfo(user.getEmail(), user.getHashedSecret(), salt, REALM_NAME);

	}

}
