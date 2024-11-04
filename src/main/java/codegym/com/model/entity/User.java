package codegym.com.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên người dùng không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Tên người dùng chỉ được chứa chữ cái và số, không có ký tự đặc biệt")
    private String username;


    @NotBlank(message = "Tên người dùng không được để trống")
    private String password;

    @Transient
    private String confirmPassword;

    private String email;


    private String avatar;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String interests;

    private LocalDate birthday;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserStatus> userStatuses;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Friendship> friendships;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Status> statuses;
}
