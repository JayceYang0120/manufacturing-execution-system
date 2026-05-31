package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DispatchStatus {

    CREATED("已建立"),
    ASSIGNED("已指派"),
    IN_PROGRESS("作業中"),
    PAUSED("暫停中"),
    COMPLETED("已完成");

    private final String description;

    public static DispatchStatus from(String value) {
        try {
            return DispatchStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dispatch status: " + value);
        }
    }
}