package goorm.wherebnb.config.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.wherebnb.config.auth.jwt.JwtTokenProvider;
import goorm.wherebnb.config.auth.user.CustomUserDetailsService;
import goorm.wherebnb.config.auth.dto.request.LoginRequestDto;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationFilter authenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            String url) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        setFilterProcessesUrl(url);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Optional<User> oUser = userRepository.findByEmail(loginRequestDto.getUsername());
        User user = oUser.get();

        // 사용자의 로그인 정보를 기반으로 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        Authentication authentication =
                getAuthenticationManager().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtTokenProvider.createToken(authResult);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
