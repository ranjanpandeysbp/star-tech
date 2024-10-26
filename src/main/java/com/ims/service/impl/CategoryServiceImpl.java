package com.ims.service.impl;

import com.ims.CommonUtil;
import com.ims.dto.CategoryDTO;
import com.ims.dto.ErrorDTO;
import com.ims.dto.ProductDTO;
import com.ims.entity.CategoryEntity;
import com.ims.entity.ECurrency;
import com.ims.entity.ProductEntity;
import com.ims.entity.UserEntity;
import com.ims.exception.BusinessException;
import com.ims.repository.CategoryRepository;
import com.ims.repository.UserRepository;
import com.ims.service.ImsService;
import com.ims.service.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ImsService<CategoryDTO, CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    CommonUtil commonUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CategoryDTO add(CategoryDTO input) {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(input, categoryEntity);
        UserEntity ue = userRepository.findById(input.getUserId()).get();
        categoryEntity.setAdmin(ue);
        categoryEntity = categoryRepository.save(categoryEntity);
        BeanUtils.copyProperties(categoryEntity, input);
        return input;
    }

    @Override
    public CategoryDTO update(CategoryDTO input, Long categoryId) {
        CategoryEntity pe = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Category with Id: "+categoryId))));
        BeanUtils.copyProperties(input, pe);
        pe.setUpdatedAt(LocalDate.now());
        pe.setId(categoryId);
        pe.setName(input.getName());
        pe = categoryRepository.save(pe);
        BeanUtils.copyProperties(pe, input);
        return input;
    }

    @Override
    public CategoryDTO delete(Long id) {

        CategoryEntity pe = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Category with Id: "+id))));
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(pe, categoryDTO);
        categoryRepository.deleteById(id);
        return categoryDTO;
    }

    @Override
    public CategoryDTO get(Long id) {
        CategoryEntity pe = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Category with Id: "+id))));
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(pe, categoryDTO);
        categoryDTO.setId(pe.getId());
        return categoryDTO;
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

   public List<CategoryDTO> getAllById(Long userId) {
        List<CategoryEntity> entityList = categoryRepository.findAllByAdminId(userId);
        return entityList.stream().map(categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(categoryEntity, categoryDTO);
            return categoryDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> search(CategoryDTO input) {
        List<CategoryEntity> categories = null;
        if(input.getName() != null){
            categories = categoryRepository.findAllByNameContaining(input.getName());
        }else {
            categories = new ArrayList<>();
        }
        CategoryDTO categoryDTO =  null;
        List<CategoryDTO> dtos = new ArrayList<>();
        for(CategoryEntity category: categories){
            categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);;
            categoryDTO.setId(category.getId());
            dtos.add(categoryDTO);
        }
        return dtos;
    }
}
