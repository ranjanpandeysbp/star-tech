package com.ims.entity.startech;

import com.ims.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long answerId;

    private String answerText;

    @ManyToOne
    private UserEntity contributor;

    @ManyToOne
    private RequestEntity request;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate pointsAssignedAt;
    private Integer points;
}
