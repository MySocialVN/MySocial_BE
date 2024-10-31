package codegym.com.service.user_status;

import codegym.com.model.entity.UserStatus;
import codegym.com.service.IGenerateService;

import java.util.Optional;

public interface IUserStatusService extends IGenerateService<UserStatus> {
    Optional<UserStatus> findByUserStatuses(UserStatus.USER_STATUS userStatus);

}
