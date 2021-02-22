package com.codeience.demo.jwt.repository;

import com.codeience.demo.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
