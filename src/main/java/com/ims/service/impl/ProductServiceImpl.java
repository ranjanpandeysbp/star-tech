package com.ims.service.impl;

import com.ims.dto.ProductDTO;
import com.ims.entity.*;
import com.ims.repository.*;
import com.obify.ims.entity.*;
import com.obify.ims.repository.*;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private LocationRepository locationRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MerchantManagerRepository merchantManagerRepository;
    @Autowired
    private MerchantVendorRepository merchantVendorRepository;

    @Override
    public ProductDTO add(ProductDTO input) {

        ProductEntity productEntity = new ProductEntity();
        BrandEntity be = brandRepository.findById(input.getBrandId()).get();
        Optional<CategoryEntity> ceOpt = categoryRepository.findById(input.getCategoryId());
        if(ceOpt.isPresent()){
            productEntity.setCategory(ceOpt.get());
        }
        LocationEntity le = locationRepository.findById(input.getLocationId()).get();
        MerchantManagerEntity mme = merchantManagerRepository.findById(input.getManagerId()).get();
        Optional<MerchantVendorEntity> mve = merchantVendorRepository.findById(input.getVendorId());
        if(mve.isPresent()){
            productEntity.setMerchantVendorEntity(mve.get());
        }
        productEntity.setBrand(be);
        productEntity.setLocationEntity(le);
        productEntity.setMerchantManagerEntity(mme);

        BeanUtils.copyProperties(input, productEntity);
        productEntity.setActive(true);
        productEntity = productRepository.save(productEntity);
        BeanUtils.copyProperties(productEntity, input);
        return input;
    }

    @Override
    public ProductDTO update(ProductDTO input, Long id) {
        return null;
    }

    @Override
    public ProductDTO delete(Long id) {
        return null;
    }

    @Override
    public ProductDTO get(Long id) {
        return null;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<ProductEntity> entityList = productRepository.findAll();
        return entityList.stream().map(productEntity -> {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(productEntity, productDTO);
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> search(ProductDTO input) {
        return List.of();
    }

}
