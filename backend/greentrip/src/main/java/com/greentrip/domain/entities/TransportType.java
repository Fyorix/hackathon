package com.greentrip.domain.entities;

public enum TransportType {
    VELO(0.20, 10),
    TROTINETTE(0.15, 8),
    MARCHE(0.25, 12),
    COVOITURAGE(0.10, 5),
    TRANSPORTS_COMMUNS(0.08, 4);

    private final double co2SavedPerKm;
    private final int pointsEarnedPerKm;

    TransportType(double co2SavedPerKm, int pointsEarnedPerKm) {
        this.co2SavedPerKm = co2SavedPerKm;
        this.pointsEarnedPerKm = pointsEarnedPerKm;
    }

    public double getCo2SavedPerKm() {
        return co2SavedPerKm;
    }

    public int getPointsEarnedPerKm() {
        return pointsEarnedPerKm;
    }

    public double calculateCo2Saved(double distanceKm) {
        return distanceKm * this.co2SavedPerKm;
    }

    public int calculatePointsEarned(double distanceKm) {
        return (int) Math.round(distanceKm * this.pointsEarnedPerKm);
    }

    public static TransportType fromString(String type) {
        try {
            return TransportType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
