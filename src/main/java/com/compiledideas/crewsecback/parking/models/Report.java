package com.compiledideas.crewsecback.parking.models;

import com.compiledideas.crewsecback.utils.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator",
            initialValue = 1000,            pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;

    private String name;
    private String address;
    private String houseNumber;
    private String phone;
    private String disturbingName;
    private String disturbingAddress;
    private String disturbingHouseNumber;

    private String arrived;
    private String onsite;
    private String finished;

    private ReportStatus status;

    @CreationTimestamp
    private Date createdAt;
    private String additionalInfos;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Parking parking;
}
