package com.compiledideas.crewsecback.parking.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;
    private String reference;
    private String name;
    private Date leaveDate;
    private Date joinDate;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;
}
