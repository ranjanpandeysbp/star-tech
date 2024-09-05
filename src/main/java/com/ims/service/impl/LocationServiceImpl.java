package com.ims.service.impl;

import com.ims.dto.LocationDTO;
import com.ims.entity.LocationEntity;
import com.ims.repository.LocationRepository;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements ImsService<LocationDTO, LocationDTO> {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public LocationDTO add(LocationDTO input) {
        LocationEntity locationEntity = new LocationEntity();
        BeanUtils.copyProperties(input, locationEntity);
        locationEntity.setActive(true);
        locationEntity = locationRepository.save(locationEntity);
        BeanUtils.copyProperties(locationEntity, input);
        return input;
    }

    @Override
    public LocationDTO update(LocationDTO input, Long id) {
        return null;
    }

    @Override
    public LocationDTO delete(Long id) {
        return null;
    }

    @Override
    public LocationDTO get(Long id) {
        return null;
    }

    @Override
    public List<LocationDTO> getAll() {
        List<LocationEntity> entityList = locationRepository.findAll();
        return entityList.stream().map(locationEntity -> {
            LocationDTO locationDTO = new LocationDTO();
            BeanUtils.copyProperties(locationEntity, locationDTO);
            return locationDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> search(LocationDTO input) {
        return List.of();
    }

}
