package goorm.wherebnb.config.auth.jwt;

import com.amazonaws.services.kms.model.NotFoundException;
import goorm.wherebnb.config.auth.user.CustomUserDetails;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static goorm.wherebnb.config.auth.jwt.JwtProperties.SECRET_KEY;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserRepository userRepository;

    public String createToken(Authentication authentication) {

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(authentication.getName())
                .claim("roles", roles)
                .expiration(new Date(new Date().getTime() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        String authority = claims.get("roles").toString();

        if(authority.equals("ROLE_USER")) {
            Optional<User> user = userRepository.findByEmail(claims.getSubject());
            CustomUserDetails customUserDetails = CustomUserDetails.create(Objects.requireNonNull(user.orElse(null)));
            return new UsernamePasswordAuthenticationToken(customUserDetails, user.get().getPassword(), authorities);
        } else {
            return null;
        }
    }

    public String validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return "Success";
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            return "signature is wrong.";
        } catch(ExpiredJwtException e) {
            return "token expired.";
        } catch (UnsupportedJwtException e) {
            return "token is unsupported";
        } catch (IllegalArgumentException e) {
            return "token is wrong";
        }
    }

}
