package codegym.com.service.role;

import codegym.com.model.entity.Role;
import codegym.com.service.IGenerateService;

public interface IRoleService extends IGenerateService<Role> {
    Role findByName(Role.RoleType name);


}
