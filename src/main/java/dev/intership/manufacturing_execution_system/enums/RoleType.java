package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    
    ADMIN("管理員"),
    MANAGER("主管"),
    OPERATOR("作業員");

    private final String description;

    public static RoleType from(String value) {
        try {
            return RoleType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role type: " + value);
        }
    }
}