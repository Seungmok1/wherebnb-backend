package goorm.wherebnb.config.auth.filter;

import goorm.wherebnb.config.auth.jwt.JwtTokenProvider;
import goorm.wherebnb.config.auth.user.CustomUserDetails;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader("Authorization");

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization")
                .replace("Bearer ", "");

        if (token != null) {
            Claims claims = jwtTokenProvider.getClaims(token);
            Long id = Long.parseLong(claims.getSubject()); // getSubject 값은 users의 id값

            if (id != null) {
                Optional<User> oUser = userRepository.findById(id);
                User user = oUser.get();
                CustomUserDetails customUserDetails = CustomUserDetails.create(user);

                // OAuth 인지 일반 로그인인지 구분할 필요가 없음. 왜냐하면 password를 Authentication이 가질 필요가 없으니!!
                // JWT가 로그인 프로세스를 가로채서 인증다 해버림. (OAuth2.0이든 그냥 일반 로그인 이든)

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication); // 세션에 넣기
                return authentication;
            }
        }
        return null;
    }
}
