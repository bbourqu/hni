package org.hni.security.realm.token;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenCredentialsMatcher implements CredentialsMatcher {
	private static final Logger logger = LoggerFactory.getLogger(TokenCredentialsMatcher.class);
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		logger.info("** validating token **");
		JWTAuthenticationToken jwtToken = (JWTAuthenticationToken)token;
		if ( null == jwtToken.getUserId()) {
			return false;
		}
		return true;
	}

}
