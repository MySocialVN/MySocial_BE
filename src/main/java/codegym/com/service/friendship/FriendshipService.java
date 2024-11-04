package codegym.com.service.friendship;
import codegym.com.model.entity.Friendship;
import codegym.com.model.entity.FriendshipStatus;
import codegym.com.model.entity.User;
import codegym.com.repository.IFriendshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FriendshipService implements IFriendshipService {

    @Autowired
    private IFriendshipRepository friendshipRepository;


    public void sendFriendRequest(User user, User friend) {
        // Kiểm tra nếu yêu cầu kết bạn đã tồn tại
        if (friendshipRepository.existsByUserAndFriend(user, friend)) {
            throw new IllegalArgumentException("Friend request already exists.");
        }

        Friendship friendship = new Friendship();
        friendship.setUser(user);
        friendship.setFriend(friend);
        friendship.setFriendshipStatus(FriendshipStatus.PENDING); // Đặt trạng thái là PENDING
        friendship.setCreatedAt(new Date()); // Thiết lập thời gian hiện tại
        friendshipRepository.save(friendship); // Lưu vào cơ sở dữ liệu
    }



    @Override
    public Iterable<Friendship> findAll() {
        return friendshipRepository.findAll();
    }

    @Override
    public void save(Friendship friendship) {
        friendshipRepository.save(friendship);
    }

    @Override
    public Optional<Friendship> findById(Long id) {
        return friendshipRepository.findById(id);
    }


    @Override
    public void delete(Long id) {
        friendshipRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserAndFriend(User user, User friend) {

return friendshipRepository.existsByUserAndFriend(user, friend);    }

    @Override
    public boolean isFriend(User currentUser, User targetUser) {
        return friendshipRepository.findByUserAndFriendAndFriendshipStatus(currentUser, targetUser, FriendshipStatus.ACCEPTED).isPresent();
    }
    @Override
    public boolean isYouSendRequest(User currentUser, User targetUser) {
        return friendshipRepository.findByUserAndFriendAndFriendshipStatus(currentUser, targetUser, FriendshipStatus.PENDING).isPresent();
    }

    @Override
    public void cancelFriendRequest(User user, Long friendId) {
        // Logic để xóa lời mời kết bạn dựa trên user và friendId
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(user.getId(), friendId);
        Friendship friendshipSend = friendshipRepository.findByUserIdAndFriendId(user.getId(), friendId);

        if (friendship != null) {
            friendshipRepository.delete(friendship);
        } else if (friendshipSend != null){
            friendshipRepository.delete(friendshipSend);
        }
        else {
            throw new EntityNotFoundException("Friend request not found.");
        }
    }

    @Override
    public void acceptFriendRequest(User user, Long friendId) {
        // Tìm yêu cầu kết bạn
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(friendId, user.getId());
        if (friendship != null && friendship.getFriendshipStatus() == FriendshipStatus.PENDING) {
            friendship.setFriendshipStatus(FriendshipStatus.ACCEPTED); // Cập nhật trạng thái
            friendshipRepository.save(friendship);
        } else {
            throw new RuntimeException("Friend request not found or already accepted.");
        }
    }
}

