package com.ndayal.my_hiit.dao;

import com.ndayal.my_hiit.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
