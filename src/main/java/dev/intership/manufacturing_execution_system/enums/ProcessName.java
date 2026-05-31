package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessName {
    
    PREPARE("前製"),
    PROCESS("加工"),
    ASSEMBLY("組裝"),
    QC("品檢"),
    PACK("包裝");

    private final String description;

    public static ProcessName from(String value) {
        try {
            return ProcessName.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid process name: " + value);
        }
    }
}
