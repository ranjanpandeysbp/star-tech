package com.ims.repository;

import com.ims.entity.startech.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByRequestorId(Long requesterId);
    List<RequestEntity> findAllByCategoryId(Long categoryId);
}
