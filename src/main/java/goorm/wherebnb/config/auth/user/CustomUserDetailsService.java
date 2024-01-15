package goorm.wherebnb.config.auth.user;

import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> oUser = userRepository.findByEmail(email);

        User user = oUser
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email :" + email));
        return CustomUserDetails.create(user);
    }
}
