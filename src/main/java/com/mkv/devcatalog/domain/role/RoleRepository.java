package com.mkv.devcatalog.domain.role;

import com.mkv.devcatalog.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
