package com.greentrip.domain.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserModel extends BaseModel {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String role;

    @Column(nullable = false)
    public String password;

    @Column(name = "carbon_points_balance")
    public Integer carbonPointsBalance = 0;

    @Column(name = "total_co2_saved")
    public Double totalCo2Saved = 0.0;

    @Column(name = "total_km")
    public Double totalKm = 0.0;

    @Column(name = "strava_refresh_token")
    public String stravaRefreshToken;

    @Column(name = "work_lat")
    public Double workLat;

    @Column(name = "work_lng")
    public Double workLng;

    @Column(name = "work_start_time")
    public LocalTime workStartTime;

    @Column(name = "work_end_time")
    public LocalTime workEndTime;

    @Column(name = "strava_athlete_id")
    public Long stravaAthleteId;

    @Column(name = "strava_access_token")
    public String stravaAccessToken;

    @Column(name = "strava_token_expires_at")
    public Long stravaTokenExpiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public CompanyModel company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<TripModel> trips = new ArrayList<>();
}
