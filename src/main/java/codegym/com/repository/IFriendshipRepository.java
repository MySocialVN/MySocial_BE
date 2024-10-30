package codegym.com.repository;

import codegym.com.model.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFriendshipRepository extends JpaRepository<Friendship, Long> {

}
