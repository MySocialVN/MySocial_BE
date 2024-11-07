package codegym.com.service.friendship;

import codegym.com.model.DTO.FriendDTO;
import codegym.com.model.entity.Friendship;
import codegym.com.model.entity.User;
import codegym.com.service.IGenerateService;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFriendshipService extends IGenerateService<Friendship> {
    public void sendFriendRequest(User user, User friend);
    public void acceptFriendRequest(User user, Long friendId);
    boolean existsByUserAndFriend(User user, User friend);
    public boolean isFriend(User currentUser, User targetUser);
    public boolean isYouSendRequest(User currentUser, User targetUser);
    public void cancelFriendRequest(User user, Long friendId);

    int countAcceptedFriends(@Param("userId") Long userId);
    int countCommonFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);


}
