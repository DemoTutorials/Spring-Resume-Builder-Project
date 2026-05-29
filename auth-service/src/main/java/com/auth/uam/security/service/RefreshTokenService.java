package com.auth.uam.security.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth.uam.entity.Permission;
import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
import com.auth.uam.exception.ResourceNotFoundExceptionGeneric;
import com.auth.uam.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class RefreshTokenService {

	@Autowired
	UserRepository userRepository;

	public static final String SECRET = "655368566D597133743677397A244326452948404D635166546A576E5A723475";

	/* Genrate Tooken */

//	public String genrateToken(String userName) {
//
//		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(userName,false);
//		User user = findByUserName.get();
//
//		Map<String, Object> claims = new HashMap<>();
//		
//		claims.put("name", user.getFirstName()+" "+user.getLastName());
//		claims.put("user_name", user.getUserName());
//		claims.put("userId", user.getUserId());
//		claims.put("email", user.getEmail());
//		claims.put("permission", getPermissionObject(user.getRole()));
//		
//		
//		return creatToken(claims, userName);
//	}
	
	public String genrateRefreshToken(String userName) {

		Optional<User> findByUserName = userRepository.findByUserNameIgnoreCaseAndIsDeleted(userName,false);
		if (findByUserName.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid user name.");
		} 
		User user = findByUserName.get();

		Map<String, Object> claims = new HashMap<>();
		claims.put("user_name", user.getUserName());
		claims.put("userId", user.getUserId());
		
		return creatToken(claims, userName);
	}

	private Object getPermissionObject(List<Roles> role) {
//		String rr = "";
		List<String> permissionList = new ArrayList<String>();
		for (Roles roles : role) {

			for (Permission permission : roles.getPermission()) {
//				rr = rr + permission.getPermissionName() + ",";
				permissionList.add(permission.getPermissionName());
			}

		}
//		rr = rr.substring(0, rr.length() - 1);
//		System.out.println("permission List conversion :" + rr);
//		return rr;
		
		List<String> removeDuplicates = permissionList.stream().distinct().collect(Collectors.toList());
		return removeDuplicates;
	}

	private String creatToken(Map<String, Object> claims, String userName) {

		return Jwts.builder().setClaims(claims).setSubject(userName)

				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 4800000/2))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/* Extract Token */

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
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
	public Boolean validateToken(String token, UserDetails userDetails) {
		
//		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String token) {

		Claims claims = extractAllClaims(token);

		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get("permission").toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return authorities;

	}

	public void tokenValidate(String token) {
		 Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public String getUserName(String token) {

		Claims claims = extractAllClaims(token);
		String userName =claims.get("user_name").toString();
//		System.out.println("user_name :"+userName);
		
		return userName;

	}
}
