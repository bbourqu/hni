package org.hni.admin.filter;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.hni.common.Constants;
import org.hni.security.realm.token.JWTAuthenticationToken;
import org.hni.security.realm.token.JWTTokenFactory;
import org.hni.user.dao.UserDAO;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.MissingClaimException;

/**
 * This filter is bound to all service calls requiring them all to present
 * a valid JWT token before they can proceed.
 * 
 * @author j2parke
 *
 */
public class JWTTokenAuthenticatingFilter extends AuthenticatingFilter {
	private static final Logger logger = LoggerFactory.getLogger(JWTTokenAuthenticatingFilter.class);
	private static final String TOKEN_HEADER = "X-hni-token";	
	// TODO: make these values dynamically injected
	private static final String KEY = "YbpWo521Z/aF7DqpiIpIHQ==";
	private static final String ISSUER = "test-issuer";

	@Inject private UserDAO userDao;
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String tokenValue = httpRequest.getHeader(TOKEN_HEADER);
		logger.info("validating token with "+tokenValue);
		try {
			// if the token is valid we'll put the claims onto the ThreadLocal for processing by the TokenRealm
			Claims claims = JWTTokenFactory.decode(tokenValue, KEY, ISSUER);
			Long userId = new Long(claims.get(Constants.USERID, Integer.class).longValue());

			// place the pre-calc permissions onto the Thread.local so we don't have to calc them again
			ThreadContext.put(Constants.PERMISSIONS, claims.get(Constants.PERMISSIONS, String.class));

			User user = userDao.get(userId);
			if ( null != user ) {
				logger.info(String.format("Found user %s for the token.  Authenticating...", user.getEmail()));
				return new JWTAuthenticationToken(user.getEmail(), userId);
			}
		} catch(MissingClaimException | IncorrectClaimException | ExpiredJwtException e) {
			// let this fall through so the authN fails
			logger.error("Not able to validate token due to "+e.getMessage());
		} 
		logger.info("token authenication failed...");
		return new JWTAuthenticationToken("", null);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		// force the token auth on every request
		return false;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean loggedIn = executeLogin(request, response);
		return loggedIn;
	}

}
