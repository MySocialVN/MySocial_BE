package codegym.com.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {
    private Long id;
    private String avatar;
    private String fullName;
    private int mutualFriendCount;
}

