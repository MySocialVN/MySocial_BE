package codegym.com.controller.user;

import codegym.com.model.DTO.UserPrinciple;
import codegym.com.model.DTO.UserProfileDTO;
import codegym.com.model.DTO.UserProfileMapper;
import codegym.com.model.entity.User;
import codegym.com.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu không khớp!");
        }

        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng!");
        }

        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username đã được sử dụng");
        }

        String registrationResult = userService.registerNewUser(user);
        if (registrationResult.equals("Đăng ký tài khoản thành công!")) {
            return ResponseEntity.ok(registrationResult);
        } else {
            System.out.println("Đăng ký thật bại: " + registrationResult);
            return ResponseEntity.badRequest().body(registrationResult);
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserProfileDTO user, BindingResult result) {
        // Kiểm tra lỗi trong binding
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserPrinciple) {
            UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
            id = userDetails.getId(); // Lấy ID từ UserPrinciple
        }

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> optionalCurrentUser = userService.findById(id);
        if (!optionalCurrentUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại! ");
        }
        User currentUser = optionalCurrentUser.get();

        // Cập nhật thông tin người dùng
        userService.updateUser(currentUser, user);
        return ResponseEntity.ok().body("Cập nhập thành công thông tin người dùng");
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserPrinciple) {
            UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
            id = userDetails.getId();
        }

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> optionalCurrentUser = userService.findById(id);

        User currentUser = optionalCurrentUser.get();

        UserProfileDTO userProfileDTO = userProfileMapper.toDTO(currentUser);

        return ResponseEntity.ok(userProfileDTO);
    }




}
