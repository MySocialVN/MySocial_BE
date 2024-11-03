package codegym.com.service.friendship;

import codegym.com.model.entity.Friendship;
import codegym.com.model.entity.User;
import codegym.com.service.IGenerateService;

public interface IFriendshipService extends IGenerateService<Friendship> {
    public void sendFriendRequest(User user, User friend);
    public void rejectFriendRequest(Friendship friendship);
    public void acceptFriendRequest(Friendship friendship);
    boolean existsByUserAndFriend(User user, User friend);
    public boolean isFriend(User currentUser, User targetUser);
    public boolean isYouSendRequest(User currentUser, User targetUser);
    public void cancelFriendRequest(User user, Long friendId);
}
