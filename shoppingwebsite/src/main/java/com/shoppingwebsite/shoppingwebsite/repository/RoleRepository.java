package com.shoppingwebsite.shoppingwebsite.repository;

import com.shoppingwebsite.shoppingwebsite.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(Long id);

    Role findByName(String name);
}