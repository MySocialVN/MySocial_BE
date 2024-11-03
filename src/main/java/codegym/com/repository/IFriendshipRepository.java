package codegym.com.repository;

import codegym.com.model.entity.Friendship;
import codegym.com.model.entity.FriendshipStatus;
import codegym.com.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndFriend(User user, User friend);
    public Optional<Friendship> findById(Long id);
    boolean existsByUserAndFriend(User user, User friend);

    Optional<Friendship> findByUserAndFriendAndFriendshipStatus(User user, User friend, FriendshipStatus friendshipStatus);

    Friendship findByUserIdAndFriendId(Long userId, Long friendId);

}
