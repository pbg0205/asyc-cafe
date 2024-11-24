package com.cooper;

public enum OrderStatus {
    WAITING("대기중"),
    IN_PROGRESS("제작중"),
    COMPLETED("완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
} 
