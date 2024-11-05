package codegym.com.model.entity;

import codegym.com.model.enums.PrivacyLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // Ngăn vòng lặp khi User được serialize qua Status

    private User user;

    private String content;

    @OneToMany(mappedBy = "status")
    private List<StatusImage> images;

    @Enumerated(EnumType.STRING)  // Chỉ định lưu giá trị Enum dưới dạng String trong database
    private PrivacyLevel privacy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "status")
    @JsonManagedReference
    private List<Comment> comments;

}
