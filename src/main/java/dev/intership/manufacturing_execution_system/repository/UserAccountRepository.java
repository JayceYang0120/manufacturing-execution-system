package dev.intership.manufacturing_execution_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.intership.manufacturing_execution_system.entity.UserAccount;
import dev.intership.manufacturing_execution_system.enums.RoleType;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @Query("""
        SELECT u FROM UserAccount u
        WHERE u.role = :role AND u.enabled = true
    """)
    List<UserAccount> findByRole(RoleType role);

}