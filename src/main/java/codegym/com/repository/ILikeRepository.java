package codegym.com.repository;

import codegym.com.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILikeRepository extends JpaRepository<Like, Long> {

}
