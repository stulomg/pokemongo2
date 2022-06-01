package com.springbootcallingexternalapi.LeagueOfLegends.JWT;

import com.springbootcallingexternalapi.LeagueOfLegends.Models.SecurityUserMainModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Generate the JWT for the Login.
 */
@Component
public class JwtProvider {

  private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private int expiration;

  /**
   * Function that generates the JWT.
   */
  public String generateToken(Authentication authentication) {
    SecurityUserMainModel securityUserMainModel =
        (SecurityUserMainModel) authentication.getPrincipal();
    return "Bearer " + Jwts.builder().setSubject(securityUserMainModel.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String getUserNameFromtoken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Validate the JWT.
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("malformed token");
    } catch (UnsupportedJwtException e) {
      logger.error("token not supported");
    } catch (ExpiredJwtException e) {
      logger.error("expired token");
    } catch (IllegalArgumentException e) {
      logger.error("empty token");
    } catch (SignatureException e) {
      logger.error("token signing error");
    }
    return false;
  }
}