package com.travel.mapper;

import com.travel.entity.User;
import com.travel.dto.UserWithReservationsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();

    User findById(@Param("id") Long id);

    User findByUsername(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    List<User> findByRole(@Param("role") String role);

    List<User> findEnabledUsers();

    List<User> searchUsers(@Param("name") String name);

    List<User> findByCreatedAtAfter(@Param("startDate") LocalDateTime startDate);

    boolean existsByUsername(@Param("username") String username);

    boolean existsByEmail(@Param("email") String email);

    int insert(User user);

    int update(User user);

    int deleteById(@Param("id") Long id);

    long countByRole(@Param("role") String role);

    List<UserWithReservationsDto> findUsersWithReservations();
}