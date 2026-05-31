package dev.intership.manufacturing_execution_system.util;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.intership.manufacturing_execution_system.security.CustomUserDetails;

public class SecurityUtil {

    public static UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    public static String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }

    private static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("User not authenticated");
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }
}