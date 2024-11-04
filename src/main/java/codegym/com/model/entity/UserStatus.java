package codegym.com.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_status")
@Data
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private USER_STATUS userStatuses;

    public enum USER_STATUS {
        ACTIVE,
        SUSPENDED
    }
}
