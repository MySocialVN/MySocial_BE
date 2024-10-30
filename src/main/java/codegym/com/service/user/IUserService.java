package codegym.com.service.user;

import codegym.com.model.entity.User;
import codegym.com.service.IGenerateService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGenerateService<User>, UserDetailsService {
    User findByUsername(String username);

}
