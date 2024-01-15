package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUserId(Long id);

    Optional<User> findByEmail(String Email);
}
