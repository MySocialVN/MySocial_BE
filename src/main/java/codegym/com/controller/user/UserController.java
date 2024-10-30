package codegym.com.controller.user;

import codegym.com.model.DTO.UserProfileMapper;
import codegym.com.service.user.IUserService;
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


}
