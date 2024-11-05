package codegym.com.model.DTO;

import codegym.com.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
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
        // `isFriend` sẽ được thiết lập ở logic khác
        return dto;
    }
}