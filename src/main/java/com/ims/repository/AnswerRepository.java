package com.ims.repository;

import com.ims.entity.startech.AnswerEntity;
import com.ims.entity.startech.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    List<AnswerEntity> findAllByRequestRequestId(Long requestId);

}
