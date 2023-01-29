package com.dtchin2.onthedotapp.service;

import com.dtchin2.onthedotapp.entity.Role;
import com.dtchin2.onthedotapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RetrieveUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userService.findUserByUserName(userName);
//        List<GrantedAuthority> authorities = getUserAuthority((List<Role>) user.getRole());
//        return buildUserForAuthentication(user, authorities);
//    }
//
//    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles){
//        List<GrantedAuthority> roles = new ArrayList<>();
//        for(Role role: userRoles){
//            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
//        return grantedAuthorities;
//    }
//
//    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(),
//                user.getUserPassword(),
//                user.getUserIsActive(), true, true, true, authorities
//        );
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userService.findUserByUserName(userName);
            GrantedAuthority authority = getUserAuthority(user.getRole());
            System.out.println("User was Found");
            System.out.println(user);
            return buildUserForAuthentication(user, authority);
        }catch (NullPointerException e){
            System.out.println("User was not found!");
            return null;
        }
    }

    private GrantedAuthority getUserAuthority(Role userRole){
        try {
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(userRole.getRoleName());

            return simpleGrantedAuthority;
        }catch (NullPointerException e){
            System.out.println("User Is Not Found");
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private UserDetails buildUserForAuthentication(User user, GrantedAuthority authoritiy) {
        // make the GrantedAuthority 'authority' into an arraylist
        // to support this method
        List<GrantedAuthority> grantedAuthorities =
                new ArrayList<>();
        grantedAuthorities.add(authoritiy);

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                user.getUserIsActive(), true,
                true,
                true,
                grantedAuthorities
        );
    }
}
