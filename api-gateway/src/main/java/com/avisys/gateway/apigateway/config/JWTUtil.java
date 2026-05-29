package com.avisys.gateway.apigateway.config;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	
	public static final String SECRET = "655368566D597133743677397A244326452948404D635166546A576E5A723475";
	
	private String creatToken(Map<String, Object> claims, String userName) {

		return Jwts.builder().setClaims(claims).setSubject(userName)

				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000/2))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/* Extract Token */

	public String getUsernameFromToken(String token) {
//		return extractClaim(token, Claims::getSubject);
		try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            return null; // Token is expired, return null
        }
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	// validate token 
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

// check token expiry

public Boolean validateToken(String token) {
 
	
		return !isTokenExpired(token);
	

		
	
}


public List<String> getPermission(String authToken) {
	Claims claims = extractAllClaims(authToken);
	 return claims.get("permission", List.class);
	
	
}


public String getUserId(String token) {

	Claims claims = extractAllClaims(token);
	String userId =claims.get("userId").toString();
	System.out.println("userId :"+userId);
	
	return userId;

}

public String getUserRole(String token) {
	Claims claims = extractAllClaims(token);
	String userId =claims.get("userRole").toString();
	System.out.println("userRole :"+userId);
	
	return userId;
}

	
//public Collection<? extends GrantedAuthority> getAuthorities(String token) {
//
//	Claims claims = extractAllClaims(token);
//	String roles =claims.get("permission").toString();
//	System.out.println(roles);
//	roles = roles.replace("[","");
//	 System.out.println(roles);
//	 roles = roles.replace("]","");
//	 System.out.println(roles);
//	 roles = roles.replaceAll("\\s", "");
//	 System.out.println(roles);
//	final Collection<? extends GrantedAuthority> authorities = Arrays
//			.stream(roles.split(",")).map(SimpleGrantedAuthority::new)
//			.collect(Collectors.toList());
//	return authorities;
//
//}

//    @Value("${springbootwebfluxjjwt.jjwt.secret}")
//    private String secret;
//
//    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
//    private String expirationTime;
//
//    private Key key;
//
//    @PostConstruct
//    public void init() {
//        this.key = (Key) Keys.hmacShaKeyFor(secret.getBytes());
//    }
//
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKeyResolver((SigningKeyResolver) key).build().parseClaimsJws(token).getBody();
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getAllClaimsFromToken(token).getExpiration();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public String generateToken(User user) {
//        Map<String, Object> claims = new HashMap<>();
////        claims.put("role", user.getRoles());
//        return doGenerateToken(claims, user.getUsername());
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String username) {
//        Long expirationTimeLong = Long.parseLong(expirationTime); //in second
//        final Date createdDate = new Date();
//        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(createdDate)
//                .setExpiration(expirationDate)
//                .signWith(key)
//                .compact();
//    }
//
//    public Boolean validateToken(String token) {
//        return !isTokenExpired(token);
//    }

}