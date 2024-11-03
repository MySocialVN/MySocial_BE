package codegym.com.service.user;

import codegym.com.model.DTO.UserProfileDTO;
import codegym.com.model.entity.User;
import codegym.com.service.IGenerateService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGenerateService<User>, UserDetailsService {
    User findByUsername(String username);

    String registerNewUser(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    public void updateUser(User currentUser, UserProfileDTO userProfileDTO);

    boolean changePassword(User user, String oldPassword, String newPassword);

    Iterable<User> findByFullNameContainingIgnoreCase(String username);

}
