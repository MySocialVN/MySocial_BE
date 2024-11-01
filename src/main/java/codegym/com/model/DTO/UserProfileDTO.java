package codegym.com.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    private String address;
    private String phoneNumber;
    private String email;
    private String interests;
    private LocalDate birthday; // Định dạng dd-MM-yyyy khi serialize
}
