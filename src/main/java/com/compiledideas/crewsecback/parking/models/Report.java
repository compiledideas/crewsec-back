package com.compiledideas.crewsecback.parking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;

    private String fullname;
    private int houseNumber;
    private int reportedHouseNumber;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Parking parking;

    private String description;
}
