package goorm.wherebnb.config.auth.user.oauth2;

import goorm.wherebnb.config.auth.exception.OAuth2AuthenticationProcessingException;
import goorm.wherebnb.config.auth.user.CustomUserDetails;
import goorm.wherebnb.domain.dao.AuthProvider;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("OAuth 중...");

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
         } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        log.info(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getId(), oAuth2UserInfo.getName());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            log.info("이메일이 비어있음");
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        log.info("이메일로 유저 찾기 진행중)");
        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            log.info("유저에다가 userRepository에서 가지고 온 값 넣는중");
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                log.info("프로바이더 같은지 확인중");
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            log.info("자료 달라서 업데이트 하는중...");
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            log.info("없는 유저라 등록하는 중");
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        AuthProvider provider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        String providerId = oAuth2UserInfo.getId();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String imageUrl = oAuth2UserInfo.getImageUrl();
        String role = "USER";
        log.info("새로운 유저 정보 다 찾아 대기 중");

        User user = User.createNewUser(provider, providerId, name, email, imageUrl, role);
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.updateUserWithOAuth2UserInfo(oAuth2UserInfo);
        return userRepository.save(existingUser);
    }
}
