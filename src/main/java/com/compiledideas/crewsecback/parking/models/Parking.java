package com.compiledideas.crewsecback.parking.models;

import com.compiledideas.crewsecback.security.models.User;
import com.compiledideas.crewsecback.utils.enums.PriceUnit;
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
    private String capacity;
    private String nbrOfEmptySpots;
    private String nbrOfParkingSpots;
    private boolean security;
    private double latitude;
    private double longitude;
    private double price;
    private PriceUnit priceUnit;
    private String image;
    private String website;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parking")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "parking")
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "parking")
    private List<Markulera> markuleras = new ArrayList<>();
}
