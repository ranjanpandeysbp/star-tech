package com.ims.repository;

import com.ims.entity.startech.AnswerEntity;
import com.ims.entity.startech.PointsAssignedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PointRepository extends JpaRepository<PointsAssignedEntity, Long> {

    List<PointsAssignedEntity> findAllByContributorId(Long contributorId);

}
