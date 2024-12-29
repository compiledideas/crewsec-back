package com.compiledideas.crewsecback.parking.models;

import com.compiledideas.crewsecback.security.models.User;
import com.compiledideas.crewsecback.utils.enums.PriceUnit;
import jakarta.persistence.*;

@Entity
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private String id;

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
}
