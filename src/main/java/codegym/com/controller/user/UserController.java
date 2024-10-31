package codegym.com.controller.user;

import codegym.com.model.DTO.UserProfileMapper;
import codegym.com.model.entity.User;
import codegym.com.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    private final UserProfileMapper userProfileMapper;
    public UserController(IUserService userService, UserProfileMapper userProfileMapper) {
        this.userService = userService;
        this.userProfileMapper = userProfileMapper;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            // Return the validation errors
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        // Check if passwords match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu không khớp!");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng!");
        }

        // Kiểm tra xem username đã tồn tại chưa
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username đã được sử dụng");
        }

        // Call service to register the user
        String registrationResult = userService.registerNewUser(user);
        if (registrationResult.equals("Đăng ký tài khoản thành công!")) {
            return ResponseEntity.ok(registrationResult);
        } else {
            System.out.println("Đăng ký thật bại: " + registrationResult);
            return ResponseEntity.badRequest().body(registrationResult);
        }
    }

}
