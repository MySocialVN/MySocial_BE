package codegym.com.controller.friendship;

import codegym.com.model.DTO.UserPrinciple;
import codegym.com.model.entity.FriendshipStatus;
import codegym.com.model.entity.User;
import codegym.com.model.entity.Friendship;

import codegym.com.service.friendship.IFriendshipService;
import codegym.com.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    @Autowired
    private IFriendshipService friendshipService;

    @Autowired
    private IUserService userService; // Giả sử bạn có một UserService để tìm kiếm người dùng


    // FriendshipController
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody Map<String, Long> requestBody, Authentication authentication) {
        Long friendId = requestBody.get("friendId");

        // Kiểm tra friendId hợp lệ
        if (friendId == null) {
            return ResponseEntity.badRequest().body("Friend ID cannot be null.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        User friend = userService.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("Friend not found."));

        friendshipService.sendFriendRequest(user, friend);
        return ResponseEntity.ok("Friend request sent.");
    }

    @DeleteMapping("/request/{friendId}")
    public ResponseEntity<String> cancelFriendRequest(@PathVariable Long friendId, Authentication authentication) {
        // Kiểm tra xác thực
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        try {
            // Xóa lời mời kết bạn
            friendshipService.cancelFriendRequest(user, friendId);
            return ResponseEntity.ok("Friend request canceled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel friend request.");
        }
    }

    @PutMapping("/accept/{friendId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long friendId, Authentication authentication) {
        // Kiểm tra xác thực
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        try {
            // Chấp nhận lời mời kết bạn
            friendshipService.acceptFriendRequest(user, friendId);
            return ResponseEntity.ok("Friend request accepted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to accept friend request.");
        }
    }





}
