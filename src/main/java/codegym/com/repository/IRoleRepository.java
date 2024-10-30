package codegym.com.repository;

import codegym.com.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(Role.RoleType name);}
