package com.obss_final_project.e_commerce.repository;

import com.obss_final_project.e_commerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByNameIn(List<String> list);

    Role findByName(String user);
}
