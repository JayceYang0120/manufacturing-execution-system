package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogType {

    START("開工", true),
    RESUME("復工", true),
    PAUSE("暫停", false),
    COMPLETE("完工", false),

    ISSUE("異常", false),
    MACHINE_DOWN("設備故障", false),
    MATERIAL_SHORTAGE("物料短缺", false),

    NOTE("備註", false);

    private final String description;
    private final boolean working;

    public static LogType from(String value) {
        try {
            return LogType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid log type: " + value);
        }
    }

    public boolean isWorking() {
        return working;
    }
}
