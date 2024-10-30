package codegym.com.repository;

import codegym.com.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
}
