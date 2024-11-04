package codegym.com.controller.user;

import codegym.com.model.DTO.*;
import codegym.com.model.entity.User;
import codegym.com.service.friendship.IFriendshipService;
import codegym.com.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    private final UserProfileMapper userProfileMapper;
    private final UserMapper userMapper;

    @Autowired
    private IFriendshipService friendshipService;

    public UserController(IUserService userService, UserProfileMapper userProfileMapper, UserMapper userMapper) {
        this.userService = userService;
        this.userProfileMapper = userProfileMapper;
        this.userMapper = userMapper;
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrentUserById(@PathVariable("id") Long id) {
        Optional<User> optionalCurrentUser = userService.findById(id);
        if (optionalCurrentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại!");
        }

        User currentUser = optionalCurrentUser.get();
        UserProfileDTO userProfileDTO = userProfileMapper.toDTO(currentUser);
        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping()
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        List<User> users = (List<User>) userService.findAll();
        List<UserProfileDTO> userProfileDTOS = new ArrayList<>();

        for (User user : users) {
            UserProfileDTO userProfileDTO = userProfileMapper.toDTO(user);
            userProfileDTOS.add(userProfileDTO);
        }

        return ResponseEntity.ok(userProfileDTOS);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword){

        // Lấy thông tin người dùng đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Lấy thông tin người dùng từ username
        User currentUser = userService.findByUsername(currentUsername);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
        }

        boolean isUpdated = userService.changePassword(currentUser, oldPassword, newPassword);

        if(isUpdated){
            return ResponseEntity.ok("Thay đổi mật khẩu thành công!");
        } else {
            return ResponseEntity.badRequest().body("Thay đổi mật khẩu thất bại!");
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam("name") String name) {
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

        List<User> users = (List<User>) userService.findByFullNameContainingIgnoreCase(name);
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            if (user.getId() == id) {
                continue;
            }
            UserDTO userDTO = userMapper.toDTO(user);
            boolean isFriend = friendshipService.isFriend(currentUser, user)||friendshipService.isFriend(user, currentUser);
            boolean isYouSendRequest = friendshipService.isYouSendRequest(currentUser, user);
            boolean isYourSendRequest = friendshipService.isYouSendRequest(user, currentUser);
            userDTO.setFriend(isFriend);
            userDTO.setYouSendRequest(isYouSendRequest);
            userDTO.setYourSendRequest(isYourSendRequest);
            userDTOS.add(userDTO);
        }

        return ResponseEntity.ok(userDTOS);
    }


}








