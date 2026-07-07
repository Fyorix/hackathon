package com.greentrip.domain.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
public class CompanyModel extends BaseModel {

    @Column(nullable = false, unique = true)
    public String name;

    @Column(name = "siren_number", nullable = false, unique = true)
    public String sirenNumber;

    @Column(name = "total_employees")
    public Integer totalEmployees = 0;

    @Column(name = "total_co2_saved")
    public Double totalCo2Saved = 0.0;

    @Column(name = "total_points")
    public Integer totalPoints = 0;

    @Column(name = "total_km")
    public Double totalKm = 0.0;

    @Column(name = "logo_path")
    public String logoPath = "default.png";

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longitude")
    public Double longitude;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserModel> users = new ArrayList<>();
}
