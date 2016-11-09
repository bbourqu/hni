package org.hni.security.realm.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * This token gets created in the JWTTokenAuthenticatingFilter which is bound
 * to all of the service calls.
 * @author j2parke
 *
 */
public class JWTAuthenticationToken implements AuthenticationToken {

	private static final long serialVersionUID = 7749443798352671309L;
	private String principal;
	private Long userId;
	
	public JWTAuthenticationToken(String principal, Long userId) {
		this.principal = principal;
		this.userId = userId;
	}
	
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return "";
	}

	public Long getUserId() {
		return userId;
	}

}
