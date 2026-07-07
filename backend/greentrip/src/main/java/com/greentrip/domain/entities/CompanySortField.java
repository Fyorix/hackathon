package com.greentrip.domain.entities;

public enum CompanySortField {
    CO2("totalCo2Saved"),
    POINTS("totalPoints"),
    KM("totalKm"),
    EMPLOYEES("totalEmployees");

    private final String fieldName;

    CompanySortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
