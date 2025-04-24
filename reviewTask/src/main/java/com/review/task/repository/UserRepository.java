package com.review.task.repository;

import com.review.task.entity.User;
import com.review.task.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Page<User> findAllByAccessRole(Role accessRole, Pageable pageable);

    Page<User> findAllByNameAndAccessRole(String name, Role accessRole, Pageable pageable);
}
