package codegym.com.repository;

import codegym.com.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusRepository extends JpaRepository<Status, Long> {

}
