package com.ims;

import com.ims.entity.ERole;
import com.ims.entity.RoleEntity;
import com.ims.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Startup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

       Optional<RoleEntity> optRoleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
       if(optRoleAdmin.isEmpty()){
           RoleEntity role = new RoleEntity();
           role.setName(ERole.ROLE_ADMIN);
           roleRepository.save(role);
       }

        Optional<RoleEntity> optRoleMe = roleRepository.findByName(ERole.ROLE_REQUESTER);
        if(optRoleMe.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_REQUESTER);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleVe = roleRepository.findByName(ERole.ROLE_CONTRIBUTOR);
        if(optRoleVe.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_CONTRIBUTOR);
            roleRepository.save(role);
        }
    }
}
