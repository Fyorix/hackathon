package com.greentrip.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class UserModel extends BaseModel {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String role;

    @Column(name = "carbon_points_balance")
    public Integer carbonPointsBalance = 0;

    @Column(name = "total_co2_saved")
    public Double totalCo2Saved = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public CompanyModel company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<TripModel> trips = new ArrayList<>();
}
