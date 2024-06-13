package com.richstone.cargo.repository;

import com.richstone.cargo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);

    Optional<User> findById(Long id);
    @Query("SELECT u FROM User u WHERE u.isEnabled = false")
    Optional<List<User>> findInactiveDrivers();

    @Query("SELECT u FROM User u WHERE u.role = 'DISPATCHER'")
    Optional<List<User>> findDispatchers();

    @Query("SELECT u FROM User u WHERE u.role = 'DRIVER'AND u.isEnabled = true AND u.isDeleted = false" )
    Optional<List<User>> findDrivers();

    @Query("SELECT u FROM User u WHERE u.isDeleted = true")
    Optional<List<User>> findDeletedDrivers();

    @Query("SELECT u FROM User u WHERE u.isEnabled = false")
    Page<User> findInactiveDrivers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role = 'DRIVER'AND u.isEnabled = true AND u.isDeleted = false" )
    Page<User> findDrivers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isDeleted = true")
    Page<User> findDeletedDrivers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role = 'DISPATCHER'")
    Page<User> findDispatchers(Pageable pageable);

}
