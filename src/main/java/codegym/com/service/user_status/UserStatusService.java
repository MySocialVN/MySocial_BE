package codegym.com.service.user_status;

import codegym.com.model.entity.UserStatus;
import codegym.com.repository.IUserStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserStatusService implements IUserStatusService {
    private final IUserStatusRepository iUserStatusRepository;

    public UserStatusService(IUserStatusRepository iUserStatusRepository) {
        this.iUserStatusRepository = iUserStatusRepository;
    }

    @Override
    public Iterable<UserStatus> findAll() {
        return iUserStatusRepository.findAll();
    }

    @Override
    public void save(UserStatus userStatus) {
        iUserStatusRepository.save(userStatus);
    }

    @Override
    public Optional<UserStatus> findById(Long id) {
        return iUserStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        iUserStatusRepository.deleteById(id);
    }

    @Override
    public Optional<UserStatus> findByUserStatuses(UserStatus.USER_STATUS userStatus) {
        return iUserStatusRepository.findByUserStatuses(userStatus);
    }
}
