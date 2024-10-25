package com.ims.entity.startech;

import com.ims.entity.CategoryEntity;
import com.ims.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "points_assigned")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointsAssignedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pointsId;

    private Integer points;

    @ManyToOne
    private UserEntity contributor;

    @ManyToOne
    private CategoryEntity category;

    private LocalDateTime createdAt;

}
