package com.jsh.communityBoard.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * During the Login Process, email account returned.
     * By this Email, check weather the user has benn already created or not.
     */
    Optional<User> findByEmail(String email);
}
