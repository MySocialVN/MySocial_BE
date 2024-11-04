package codegym.com.repository;

import codegym.com.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Iterable<User> findByFullNameContainingIgnoreCase(String username);

    User findByEmail(String email);


}
