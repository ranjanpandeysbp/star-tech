package com.ims.service.impl;

import com.ims.dto.BrandDTO;
import com.ims.entity.BrandEntity;
import com.ims.repository.BrandRepository;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements ImsService<BrandDTO, BrandDTO> {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public BrandDTO add(BrandDTO input) {
        BrandEntity brandEntity = new BrandEntity();
        BeanUtils.copyProperties(input, brandEntity);
        brandEntity.setActive(true);
        brandEntity = brandRepository.save(brandEntity);
        BeanUtils.copyProperties(brandEntity, input);
        return input;
    }

    @Override
    public BrandDTO update(BrandDTO input, Long id) {
        return null;
    }

    @Override
    public BrandDTO delete(Long id) {
        return null;
    }

    @Override
    public BrandDTO get(Long id) {
        return null;
    }

    @Override
    public List<BrandDTO> getAll() {
        List<BrandEntity> entityList = brandRepository.findAll();
        return entityList.stream().map(brandEntity -> {
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandEntity, brandDTO);
            return brandDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BrandDTO> search(BrandDTO input) {
        return List.of();
    }
}
