package com.travel.repository;

import com.travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    List<User> findByRole(User.UserRole role);
    
    List<User> findByEnabled(boolean enabled);
    
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name% OR u.username LIKE %:name%")
    List<User> findByFullNameOrUsernameContaining(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :startDate")
    List<User> findByCreatedAtAfter(@Param("startDate") java.time.LocalDateTime startDate);
} 