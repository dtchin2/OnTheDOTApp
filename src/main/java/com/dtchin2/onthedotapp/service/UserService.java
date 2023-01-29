package com.dtchin2.onthedotapp.service;

import com.dtchin2.onthedotapp.entity.Role;
import com.dtchin2.onthedotapp.entity.User;
import com.dtchin2.onthedotapp.jpa.RoleRepository;
import com.dtchin2.onthedotapp.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    public UserService(UserRepository userRepository,
//                       RoleRepository roleRepository,
//                       BCryptPasswordEncoder bCryptPasswordEncoder){
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    public User findUserByEmail(String userEmail){
        return userRepository.findByUserEmail(userEmail);
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user){
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        user.setUserIsActive(true);
        Role userRole = roleRepository.findRoleByRoleName("OWNER");
        user.setRole(userRole);
        return userRepository.save(user);
    }
}
