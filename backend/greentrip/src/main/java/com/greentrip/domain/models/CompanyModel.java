package com.greentrip.domain.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
public class CompanyModel extends BaseModel {

    @Column(nullable = false, unique = true)
    public String name;

    @Column(name = "total_employees")
    public Integer totalEmployees = 0;

    @Column(name = "total_co2_saved")
    public Double totalCo2Saved = 0.0;

    @Column(name = "unlocked_badge")
    public String unlockedBadge;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<UserModel> users = new ArrayList<>();
}
