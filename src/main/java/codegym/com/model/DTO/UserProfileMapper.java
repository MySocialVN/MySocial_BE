package codegym.com.model.DTO;

import codegym.com.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    public UserProfileDTO toDTO(User user) {
        return new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getAvatar(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getBirthday()
        );
    }
}
