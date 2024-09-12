package com.ims.service.impl;

import com.ims.CommonUtil;
import com.ims.dto.BrandDTO;
import com.ims.dto.SalesRequestData;
import com.ims.dto.SalesResponseData;
import com.ims.entity.BrandEntity;
import com.ims.entity.ProductEntity;
import com.ims.entity.SalesEntity;
import com.ims.entity.UserEntity;
import com.ims.repository.BrandRepository;
import com.ims.repository.ProductRepository;
import com.ims.repository.SalesRepository;
import com.ims.repository.UserRepository;
import com.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl  {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;

    public SalesEntity addSales(SalesEntity salesEntity) {
        ProductEntity productEntity = productRepository.findById(salesEntity.getProduct().getProductId()).get();
        productEntity.setQuantity(productEntity.getQuantity() - salesEntity.getSoldQuantity());
        productRepository.save(productEntity);
        salesEntity.setCreatedAt(LocalDateTime.now());
        Optional<UserEntity> optUser = userRepository.findById(commonUtil.loggedInUser().getId());
        salesEntity.setMerchant(optUser.get());
        return salesRepository.save(salesEntity);
    }

    public SalesResponseData getSalesInventoryDetail(SalesRequestData salesRequestData) {
        List<SalesEntity> salesEntityList = salesRepository.findByMerchantIdAndCreatedAtBetween(
                salesRequestData.getMerchantId(),salesRequestData.getStartDate(),salesRequestData.getEndDate());
        return SalesResponseData.builder().salesEntityList(salesEntityList).build();
    }
}
