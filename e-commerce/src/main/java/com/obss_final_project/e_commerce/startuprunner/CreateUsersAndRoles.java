package com.obss_final_project.e_commerce.startuprunner;


import com.obss_final_project.e_commerce.service.RoleService;
import com.obss_final_project.e_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CreateUsersAndRoles implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        roleService.saveAllRoles("USER", "ADMIN");
        userService.saveAdmin();



    }
}
