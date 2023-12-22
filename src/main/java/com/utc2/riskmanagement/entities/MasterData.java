package com.utc2.riskmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String type;
    private String value;

    @OneToMany(mappedBy = "level")
    private List<Risk> risksOfLevel = new ArrayList<>();

    @OneToMany(mappedBy = "progress")
    private List<Risk> risksOfProgress = new ArrayList<>();

    @OneToMany(mappedBy = "reportedClass")
    private List<Risk> risksOfClass = new ArrayList<>();

    @OneToMany(mappedBy = "typeRisk")
    private List<Risk> risksOfType = new ArrayList<>();

    @OneToMany(mappedBy = "type")
    private List<User> usersOfType = new ArrayList<>();
}
