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
@Table(name = "requests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    private String requestText;

    @ManyToOne
    private UserEntity requestor;

    @ManyToOne
    private CategoryEntity category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
