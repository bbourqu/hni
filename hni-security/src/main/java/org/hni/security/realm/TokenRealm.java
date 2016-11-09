package org.hni.security.realm;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.shiro.util.ThreadContext;
import org.hni.common.Constants;
import org.hni.security.realm.token.JWTAuthenticationToken;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenRealm extends PasswordRealm {
	private static final Logger logger = LoggerFactory.getLogger(TokenRealm.class);
	public static final String REALM_NAME = "tokenRealm";
	
	public TokenRealm() {
		setAuthenticationTokenClass(JWTAuthenticationToken.class);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		logger.info("Attempting TOKEN authentication of user "+token.getPrincipal());
		User user = null;
		JWTAuthenticationToken jwtToken = (JWTAuthenticationToken)token;
		
		try {
			//user = userDao.get((Long)token.getPrincipal());
			user = userDao.get(jwtToken.getUserId());
			if (null == user ) {
				logger.warn("Could not find User for principal:"+token.getPrincipal());
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

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("Attempting TOKEN authorization of user "+principals.getPrimaryPrincipal());
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			logger.warn(principals.getPrimaryPrincipal()+ " was not logged in!  Cannot authZ.");
			return null;
		}		
				
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();

		String permissions = (String)ThreadContext.get(Constants.PERMISSIONS);
						
		// TODO: deserialize the permissions and add them to the authInfo
		//authInfo.addRole(role);
		//authInfo.addStringPermission(permission);
		return authInfo;

	}


}
