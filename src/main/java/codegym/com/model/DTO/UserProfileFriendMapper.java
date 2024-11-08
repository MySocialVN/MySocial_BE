package codegym.com.model.DTO;

import codegym.com.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileFriendMapper {
    public UserProfileFriendDTO toDTO(User user) {
        UserProfileFriendDTO dto = new UserProfileFriendDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatar(user.getAvatar());
        dto.setBackground(user.getBackground());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBirthday(user.getBirthday());
        dto.setInterests(user.getInterests());
        dto.setAddress(user.getAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    };
}
