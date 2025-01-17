package com.compiledideas.crewsecback.parking.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "markulera")
@EntityListeners(AuditingEntityListener.class)
public class Markulera {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;
    private String reference;
    private String cause;
    private boolean resolved = false;

    @ManyToOne
    private Parking parking;

   @CreatedDate
    private Date createdAt;
}
