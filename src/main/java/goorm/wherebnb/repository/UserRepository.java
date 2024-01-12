package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUserId(Long id);
}
