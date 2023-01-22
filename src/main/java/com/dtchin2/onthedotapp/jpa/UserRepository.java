package com.dtchin2.onthedotapp.jpa;

import com.dtchin2.onthedotapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserEmail(String email);
    User findByUserName(String userName);
}
