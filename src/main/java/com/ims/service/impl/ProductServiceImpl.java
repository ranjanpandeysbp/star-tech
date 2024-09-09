package com.ims.service.impl;

import com.ims.CommonUtil;
import com.ims.dto.BulkUploadDTO;
import com.ims.dto.ErrorDTO;
import com.ims.dto.ProductDTO;
import com.ims.entity.*;
import com.ims.exception.BusinessException;
import com.ims.repository.*;
import com.ims.service.ImsService;
import com.ims.service.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ImsService<ProductDTO, ProductDTO> {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;
    private  UserEntity userEntity;

    @Override
    public ProductDTO add(ProductDTO input) {

        ProductEntity productEntity = new ProductEntity();
        Optional<CategoryEntity> ceOpt = categoryRepository.findById(input.getCategoryId());
        if(ceOpt.isPresent()){
            productEntity.setCategory(ceOpt.get());
        }
        BeanUtils.copyProperties(input, productEntity);
        productEntity.setActive(true);
        UserEntity userEntity = userRepository.findById((input.getMerchantId())).get();
        productEntity.setMerchant(userEntity);
        productEntity.setCurrency(ECurrency.valueOf(input.getCurrency()));
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        productEntity = productRepository.save(productEntity);
        BeanUtils.copyProperties(productEntity, input);
        return input;
    }

    @Override
    public ProductDTO update(ProductDTO input, Long id) {
        ProductEntity pe = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Product with Id: "+id))));
        BeanUtils.copyProperties(input, pe);
        pe.setCurrency(ECurrency.valueOf(input.getCurrency()));
        pe.setUpdatedAt(LocalDateTime.now());
        pe.setProductId(id);
        pe = productRepository.save(pe);
        BeanUtils.copyProperties(pe, input);
        return input;
    }

    @Override
    public ProductDTO delete(Long id) {
        ProductEntity pe = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Product with Id: "+id))));
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(pe, productDTO);
        productRepository.deleteById(id);
        return productDTO;
    }

    @Override
    public ProductDTO get(Long id) {
        ProductEntity pe = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(List.of(new ErrorDTO("NOT_FOUND", "Cannot find Product with Id: "+id))));
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(pe, productDTO);
        productDTO.setProductId(pe.getProductId());
        return productDTO;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<ProductEntity> entityList = productRepository.findAll();
        return entityList.stream().map(productEntity -> {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(productEntity, productDTO);
            productDTO.setCategoryId(productEntity.getCategory().getCategoryId());
            productDTO.setProductId(productEntity.getProductId());
            productDTO.setMerchantId(productEntity.getMerchant().getId());
            productDTO.setCurrency(productEntity.getCurrency().toString());
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> search(ProductDTO input) {
        List<ProductEntity> products = null;
        if(input.getProductName() != null){
            products = productRepository.findAllByProductNameContaining(input.getProductName());
        }else {
            products = new ArrayList<>();
        }
        ProductDTO productDTO =  null;
        List<ProductDTO> dtos = new ArrayList<>();
        for(ProductEntity product: products){
            productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);;
            productDTO.setProductId(product.getProductId());
            dtos.add(productDTO);
        }
        return dtos;
    }

    public List<ProductDTO> bulkUpload(BulkUploadDTO inventoryDetails) {
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        userEntity = userRepository.findById(userDetails.getId()).get();
        inventoryDetails.getInventoryDetails().stream().skip(1).map(inventoryDetailList->inventoryDetailList.get(4).toString()).distinct()
                .forEach(this::saveCategories);
        return null;
    }
    private void saveCategories(String category){
        CategoryEntity categoryEntity= new CategoryEntity();
        categoryEntity.setCategoryName(category);
        categoryRepository.save(categoryEntity);
    }
}
