package com.compiledideas.crewsecback.parking.models;

import com.compiledideas.crewsecback.security.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;

    private String name;
    private String description;
    private String location;
    private int capacity;
    private int nbrOfEmptySpots;
    private int nbrOfParkingSpots;
    private boolean security;
    private double latitude;
    private double longitude;
    private String image;
    private String website;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "parking")
    private List<Report> reports = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "parking")
    private List<Vehicle> vehicles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "parking")
    private List<Markulera> markuleras = new ArrayList<>();
}
