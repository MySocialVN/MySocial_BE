package codegym.com.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "message_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
