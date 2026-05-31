package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Equipment {
    
    EQUIPMENT_1("機台1"),
    EQUIPMENT_2("機台2"),
    EQUIPMENT_3("機台3"),
    EQUIPMENT_4("機台4"),
    EQUIPMENT_5("機台5"),
    EQUIPMENT_6("機台6");

    private final String description;

    public static Equipment from(String value) {
        try {
            return Equipment.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid equipment: " + value);
        }
    }
}
