package com.ims.service.impl;

import com.ims.dto.CategoryDTO;
import com.ims.entity.CategoryEntity;
import com.ims.repository.CategoryRepository;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ImsService<CategoryDTO, CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO add(CategoryDTO input) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(input, categoryEntity);
        categoryEntity.setActive(true);
        categoryEntity = categoryRepository.save(categoryEntity);
        BeanUtils.copyProperties(categoryEntity, input);
        return input;
    }

    @Override
    public CategoryDTO update(CategoryDTO input, Long id) {
        return null;
    }

    @Override
    public CategoryDTO delete(Long id) {
        return null;
    }

    @Override
    public CategoryDTO get(Long id) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        return entityList.stream().map(categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(categoryEntity, categoryDTO);
            return categoryDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> search(CategoryDTO input) {
        return List.of();
    }
}
