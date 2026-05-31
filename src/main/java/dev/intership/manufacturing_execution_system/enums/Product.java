package dev.intership.manufacturing_execution_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Product {
    
    PRODUCT_1("產品1"),
    PRODUCT_2("產品2"),
    PRODUCT_3("產品3"),
    PRODUCT_4("產品4"),
    PRODUCT_5("產品5"),
    PRODUCT_6("產品6"),
    PRODUCT_7("產品7"),
    PRODUCT_8("產品8");

    private final String description;

    public static Product from(String value) {
        try {
            return Product.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product: " + value);
        }
    }
}
