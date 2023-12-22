package com.utc2.riskmanagement.repositories;

import com.utc2.riskmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCode(String code);
}
