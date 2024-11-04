package codegym.com.repository;

import codegym.com.model.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserStatusRepository extends JpaRepository<UserStatus, Long> {
    Optional<UserStatus> findByUserStatuses(UserStatus.USER_STATUS userStatus);

}
