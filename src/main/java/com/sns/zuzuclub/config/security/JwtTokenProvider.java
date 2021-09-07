package com.sns.zuzuclub.config.security;


import com.sns.zuzuclub.domain.user.model.UserRoleType;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

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

  public long calculateDaysLeft(String jwtRefreshToken){

    Date exp = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtRefreshToken).getBody().getExpiration();
    Date now = new Date();

    long calDate = exp.getTime() - now.getTime();

    return calDate / (24 * 60 * 60 * 1000);
  }

  public void validateAccessToken(String accessToken) {
    validateToken("ACCESS_TOKEN", accessToken);
  }

  public void validateRefreshToken(String refreshToken) {
    validateToken("REFRESH_TOKEN", refreshToken);
  }

  private void validateToken(String tokenType, String token){
    String subject = getSubject(token);
    if (!tokenType.equals(subject)){
      throw new CustomException(JwtErrorCodeType.NOT_VALID_TOKEN);
    }
  }

  private String getSubject(String jwtToken) {
    return Jwts.parser()
               .setSigningKey(SECRET_KEY)
               .parseClaimsJws(jwtToken)
               .getBody()
               .getSubject();
  }
}
