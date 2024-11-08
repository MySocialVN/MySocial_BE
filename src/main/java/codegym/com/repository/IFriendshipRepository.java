package codegym.com.repository;

import codegym.com.model.entity.Friendship;
import codegym.com.model.entity.FriendshipStatus;
import codegym.com.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IFriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndFriend(User user, User friend);

    public Optional<Friendship> findById(Long id);

    boolean existsByUserAndFriend(User user, User friend);

    Optional<Friendship> findByUserAndFriendAndFriendshipStatus(User user, User friend, FriendshipStatus friendshipStatus);

    Friendship findByUserIdAndFriendId(Long userId, Long friendId);


    @Query("SELECT u FROM User u WHERE u.id IN (" +
            "SELECT f.friend.id FROM Friendship f WHERE f.user.id = :userId AND f.friendshipStatus = 'ACCEPTED' " +
            "UNION " +
            "SELECT f.user.id FROM Friendship f WHERE f.friend.id = :userId AND f.friendshipStatus = 'ACCEPTED')")
    List<User> findAllAcceptedFriends(@Param("userId") Long userId);

    @Query("SELECT COUNT(CASE WHEN f.user.id = :userId THEN f.friend.id ELSE f.user.id END) " +
            "FROM Friendship f " +
            "WHERE f.friendshipStatus = 'ACCEPTED' " +
            "AND (:userId IN (f.user.id, f.friend.id))")
    int countAcceptedFriends(@Param("userId") Long userId);


    @Query("SELECT COUNT(u) FROM User u WHERE u.id IN (" +
            "SELECT CASE " +
            "WHEN f1.user.id = :userId1 THEN f1.friend.id " +
            "ELSE f1.user.id END " +
            "FROM Friendship f1 " +
            "WHERE f1.friendshipStatus = 'ACCEPTED' AND " +
            "(:userId1 IN (f1.user.id, f1.friend.id)) " +
            ") " +
            "AND u.id IN (" +
            "SELECT CASE " +
            "WHEN f2.user.id = :userId2 THEN f2.friend.id " +
            "ELSE f2.user.id END " +
            "FROM Friendship f2 " +
            "WHERE f2.friendshipStatus = 'ACCEPTED' AND " +
            "(:userId2 IN (f2.user.id, f2.friend.id)) " +
            ")")
    int countCommonFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.id IN (" +
            "    SELECT CASE WHEN f1.user.id = :userId1 THEN f1.friend.id ELSE f1.user.id END " +
            "    FROM Friendship f1 " +
            "    WHERE f1.friendshipStatus = 'ACCEPTED' " +
            "      AND (:userId1 = f1.user.id OR :userId1 = f1.friend.id)" +
            ") " +
            "AND u.id IN (" +
            "    SELECT CASE WHEN f2.user.id = :userId2 THEN f2.friend.id ELSE f2.user.id END " +
            "    FROM Friendship f2 " +
            "    WHERE f2.friendshipStatus = 'ACCEPTED' " +
            "      AND (:userId2 = f2.user.id OR :userId2 = f2.friend.id)" +
            ")")
    List<User> findCommonFriends(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}

