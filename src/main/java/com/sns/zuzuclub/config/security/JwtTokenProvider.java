package com.sns.zuzuclub.config.security;


import com.sns.zuzuclub.domain.user.model.UserRoleType;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  private final UserDetailsService userDetailsService;

  private final String SECRET_KEY;
  private final long ACCESS_DURATION;
  private final long REFRESH_DURATION;

  public JwtTokenProvider(
      @Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
      @Value("jwt.secret-key") String secretKey,
      @Value("${jwt.access-duration}") long accessDuration,
      @Value("${jwt.refresh-duration}") long refreshDuration) {
    this.userDetailsService = userDetailsService;
    this.SECRET_KEY = Base64.getEncoder()
                            .encodeToString(secretKey.getBytes());
    this.ACCESS_DURATION = accessDuration * 1000;
    this.REFRESH_DURATION = refreshDuration * 1000;
  }

  // Jwt AccessToken 생성
  public String createJwtAccessToken(Long userPk, UserRoleType userRoleType) {
    Date now = new Date();
    return Jwts.builder()
               .setSubject("ACCESS_TOKEN")
               .setIssuedAt(now)
               .setExpiration(new Date(now.getTime() + ACCESS_DURATION))
               .setAudience(userPk.toString())
               .claim("role", userRoleType.name())
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 암호화 알고리즘
               .compact();
  }

  // Jwt RefreshToken 생성
  public String createJwtRefreshToken(Long userPk) {
    Date now = new Date();
    return Jwts.builder()
               .setSubject("REFRESH_TOKEN")
               .setIssuedAt(now)
               .setExpiration(new Date(now.getTime() + REFRESH_DURATION))
               .setAudience(userPk.toString())
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 암호화 알고리즘
               .compact();
  }

  public Authentication getAuthentication(String jwtToken) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(resolveUserPk(jwtToken));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveUserPk(String jwtToken) {
    return Jwts.parser()
               .setSigningKey(SECRET_KEY)
               .parseClaimsJws(jwtToken)
               .getBody()
               .getAudience();
  }

  public boolean isValidatedToken(String jwtToken) {

    if(jwtToken == null){
      return false;
    }

    // ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
        return true;
    } catch (ExpiredJwtException e) {
      logger.info(JwtErrorCodeType.EXPIRED_JWT_TOKEN.getMessage(), new CustomException(JwtErrorCodeType.EXPIRED_JWT_TOKEN));
    } catch (UnsupportedJwtException e) {
      logger.info(JwtErrorCodeType.UNSUPPORTED_JWT_TOKEN.getMessage(), new CustomException(JwtErrorCodeType.UNSUPPORTED_JWT_TOKEN));
    } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
      logger.info(JwtErrorCodeType.MALFORMED_JWT_TOKEN.getMessage(), new CustomException(JwtErrorCodeType.MALFORMED_JWT_TOKEN));
    }
    return false;
  }

  public long calculateDaysLeft(String jwtRefreshToken){

    Date exp = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtRefreshToken).getBody().getExpiration();
    Date now = new Date();

    long calDate = exp.getTime() - now.getTime();

    return calDate / (24 * 60 * 60 * 1000);
  }
}
