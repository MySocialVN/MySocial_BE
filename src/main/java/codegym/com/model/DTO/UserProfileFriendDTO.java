package codegym.com.model.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileFriendDTO {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    private String background;
    private String address;
    private String phoneNumber;
    private String email;
    private String interests;
    private LocalDate birthday;
    private boolean isFriend = false;
    private boolean isYouSendRequest = false;
    private boolean isYourSendRequest = false;
    private int friendCount;
    private int mutualFriendCount;

}
