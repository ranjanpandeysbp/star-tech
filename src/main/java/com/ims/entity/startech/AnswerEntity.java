package com.ims.entity.startech;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime pointsAssignedAt;
    private Integer points;
}
