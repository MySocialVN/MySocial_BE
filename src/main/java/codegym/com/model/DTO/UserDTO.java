package codegym.com.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    private String address;
    private String phoneNumber;
    private String email;
    private String interests;
    private LocalDate birthday; // Định dạng dd-MM-yyyy khi serialize
    private boolean isFriend = false;
    private boolean isYouSendRequest = false;
    private boolean isYourSendRequest = false;


    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
    // getters, setters, and constructors
}