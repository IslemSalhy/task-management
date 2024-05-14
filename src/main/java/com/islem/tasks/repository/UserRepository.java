package com.islem.tasks.repository;

import com.islem.tasks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findUserByEmailAndPassword(String email, String password);
    void deleteUserById (Integer id);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :userId")
    Optional<User> findByIdWithTasks(@Param("userId") Integer userId);

}

