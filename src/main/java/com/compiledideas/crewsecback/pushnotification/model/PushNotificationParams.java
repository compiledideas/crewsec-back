package com.compiledideas.crewsecback.pushnotification.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "push_notification_params")
public class PushNotificationParams {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "table-generator")
    @TableGenerator(name = "table-generator",
            table = "sequence_id_generator", pkColumnName = "seq_id",
            valueColumnName = "seq_value")
    private Long id;

    private String adminId;
    private String adminToken;
}
