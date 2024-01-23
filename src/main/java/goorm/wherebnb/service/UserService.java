package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Long> getWishList(Long userId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        return findUser.getWishList();
    }

    public List<Long> addWishList(Long userId, Long propertyId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        findUser.addWishList(propertyId);
        userRepository.save(findUser);

        return findUser.getWishList();
    }

    public List<Long> removeWishList(Long userId, Long propertyId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        findUser.removeWishList(propertyId);
        userRepository.save(findUser);

        return findUser.getWishList();
    }
}
