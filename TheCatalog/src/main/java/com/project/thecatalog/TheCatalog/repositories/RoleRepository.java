package com.project.thecatalog.TheCatalog.repositories;

import com.project.thecatalog.TheCatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
