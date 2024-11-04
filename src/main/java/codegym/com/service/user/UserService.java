package codegym.com.service.user;

import codegym.com.model.DTO.UserPrinciple;
import codegym.com.model.DTO.UserProfileDTO;
import codegym.com.model.entity.Role;
import codegym.com.model.entity.User;

import codegym.com.repository.IRoleRepository;
import codegym.com.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return UserPrinciple.build(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String registerNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Tên người dùng đã được sử dụng.";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email đã được đăng ký.";
        }

        // Kiểm tra xem mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return "Mật khẩu và xác nhận mật khẩu không trùng khớp.";
        }

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Đặt Role mặc định là ROLE_USER
        Role defaultRole = roleRepository.findRoleByName(Role.RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy quyền người dùng."));
        user.setRoles(Set.of(defaultRole)); // Gán role cho người dùng

        // Đặt ảnh đại diện mặc định
        user.setAvatar("https://firebasestorage.googleapis.com/v0/b/home-dn.appspot.com/o/images%2Favatar.jpg?alt=media&token=f43bdd14-8aa5-4364-afc7-509f6f72a172");
        user.setFullName(user.getUsername());

        // Lưu thông tin người dùng
        userRepository.save(user);

        return "Đăng ký tài khoản thành công!";
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override

    public void updateUser(User currentUser, UserProfileDTO userProfileDTO) {
        currentUser.setFullName(userProfileDTO.getFullName());
        if(Objects.equals(userProfileDTO.getAvatar(), null)){
            currentUser.setAvatar("https://firebasestorage.googleapis.com/v0/b/home-dn.appspot.com/o/images%2Favatar.jpg?alt=media&token=f43bdd14-8aa5-4364-afc7-509f6f72a172");
        } else {
            currentUser.setAvatar(userProfileDTO.getAvatar());
        }
        currentUser.setPhoneNumber(userProfileDTO.getPhoneNumber());
        currentUser.setAddress(userProfileDTO.getAddress());
        currentUser.setInterests(userProfileDTO.getInterests());
        currentUser.setBirthday(userProfileDTO.getBirthday());
        userRepository.save(currentUser);
    }

    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // Mã hóa mật khẩu mới và lưu lại
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    public Iterable<User> findByFullNameContainingIgnoreCase(String username) {
        return userRepository.findByFullNameContainingIgnoreCase(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
