package com.greentrip.domain.entities;

public enum UserSortField {
    CO2("totalCo2Saved"),
    POINTS("carbonPointsBalance"),
    KM("totalKm");

    private final String fieldName;

    UserSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
