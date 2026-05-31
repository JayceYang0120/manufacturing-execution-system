package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportStatus {
    
    DRAFT("草稿"),
    SUBMITTED("已提交"),
    CANCELLED("已作廢");

    private final String description;

    public static ReportStatus from(String value) {
        try {
            return ReportStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid report status: " + value);
        }
    }
}