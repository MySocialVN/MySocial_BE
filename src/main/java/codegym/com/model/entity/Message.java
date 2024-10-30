package codegym.com.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private MessageGroup group;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
