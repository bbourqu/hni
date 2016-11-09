package org.hni.security.realm.token;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTokenFactory {


	public static String encode(String key, String issuer, String subject, Long ttlMillis, Long userId, String permissions) {
		 SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		    long nowMillis = System.currentTimeMillis();
		    Date now = new Date(nowMillis);
		 
		    //We will sign our JWT with our ApiKey secret
		    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		 
		    Map<String,Object> claims = new HashMap<>();
		    claims.put("userId", userId);
		    claims.put("permissions", permissions);
		    
		    //Let's set the JWT Claims
		    JwtBuilder builder = Jwts.builder()
		                                .setIssuedAt(now)
		                                .setClaims(claims)
		                                .setSubject(subject)
		                                .setIssuer(issuer)
		                                .signWith(signatureAlgorithm, signingKey);
		 
		    //if it has been specified, let's add the expiration
		    if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		        Date exp = new Date(expMillis);
		        builder.setExpiration(exp);
		    }
		 
		    //Builds the JWT and serializes it to a compact, URL-safe string
		    return builder.compact();
	}


	public static Claims decode(String token, String key, String issuer) throws MissingClaimException, IncorrectClaimException {
		Claims claims = Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(key))
				.requireIssuer(issuer)
				.parseClaimsJws(token).getBody();
		return claims;
	}

}
