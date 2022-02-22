package com.augustojaml.appposts.config.auth.JWT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augustojaml.appposts.config.auth.exceptions.JWTAuthTokenExpiredException;
import com.augustojaml.appposts.config.auth.user.UserSecurityDetail;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

  @Value("${jwt.token.secret}")
  private String secretToken;

  @Value("${jwt.token.expiration}")
  private Long expirationToken;

  @Value("${jwt.refresh.token.secret}")
  private String secretRefreshToken;

  @Value("${jwt.refresh.token.expiration}")
  private Long expirationRefreshToken;

  @Value("${jwt.cookie.expiration}")
  private Long expirationCookie;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  HttpServletResponse response;

  @Autowired
  UsersRepository usersRepository;

  public String generateToken(UserSecurityDetail username) {
    return Jwts.builder()
        .setSubject(username.getUsername())
        .claim("authorities", this.getClaimAuthorities(username.getAuthorities()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationToken))
        .signWith(SignatureAlgorithm.HS512, secretToken.getBytes())
        .compact();
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretToken.getBytes()).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      return null;
    }
  }

  public String generateRefreshToken(UserSecurityDetail username) {
    return Jwts.builder()
        .setSubject(username.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + expirationRefreshToken))
        .signWith(SignatureAlgorithm.HS512, secretRefreshToken.getBytes())
        .compact();
  }

  public boolean isValidToken(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());
      if (username != null && expirationDate != null && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      return claims.getSubject();
    }
    return null;
  }

  private List<String> getClaimAuthorities(Collection<? extends GrantedAuthority> payload) {

    List<String> authorities = new ArrayList<>();

    for (GrantedAuthority authority : payload) {
      authorities.add(authority.toString());
    }

    return authorities;
  }

  public void addRefreshTokenInCookie(String refreshToken) {
    Cookie refreshTokenCooke = new Cookie("refresh_token", refreshToken);
    refreshTokenCooke.setHttpOnly(true);
    refreshTokenCooke.setSecure(false);
    refreshTokenCooke.setPath(this.request.getContextPath() + "/auth/refresh-token");
    refreshTokenCooke.setMaxAge(expirationCookie.intValue());
    response.addCookie(refreshTokenCooke);
  }

  public void signInWithRefreshToken() {
    Cookie[] cookies = request.getCookies();
    String refreshToken = this.getRefreshToken(cookies);
    String usernameToken = this.getUsername(refreshToken);

    if (usernameToken == null) {
      Cookie cookie = new Cookie("refresh_token", "");
      cookie.setMaxAge(0);
      throw new JWTAuthTokenExpiredException("Token expired");
    }

    UserModel userModel = this.usersRepository.findByEmail(usernameToken);

    UserSecurityDetail userDetailAuth = new UserSecurityDetail(userModel.getId(), userModel.getEmail(),
        userModel.getPassword(),
        userModel.getRoles());

    String token = this.generateToken(userDetailAuth);

    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("access-control-expose-headers", "Authorization");
  }

  public String getRefreshToken(Cookie[] cookies) {
    if ((cookies == null) || (cookies.length == 0)) {
      return null;
    }

    String cookieRefreshToken = null;

    for (int i = 0; i < cookies.length; i++) {
      if ("refresh_token".equals(cookies[i].getName()) && this.isValidToken(cookies[i].getValue())) {
        cookieRefreshToken = cookies[i].getValue();

      }
    }
    return cookieRefreshToken;
  }

}
