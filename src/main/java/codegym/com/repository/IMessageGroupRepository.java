package codegym.com.repository;

import codegym.com.model.entity.MessageGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageGroupRepository extends JpaRepository<MessageGroup, Long> {

}
