package com.greentrip.domain.models;

import com.greentrip.domain.entities.TransportType;
import jakarta.persistence.*;

@Entity
@Table(name = "TRIPS")
public class TripModel extends BaseModel {

    @Column(name = "distance_km", nullable = false)
    public Double distanceKm;

    @Column(name = "co2_saved", nullable = false)
    public Double co2Saved;

    @Column(name = "points_earned", nullable = false)
    public Integer pointsEarned;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransportType type;

    @Column(nullable = true)
    public String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserModel user;
}
