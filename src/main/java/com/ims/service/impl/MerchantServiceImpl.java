package com.ims.service.impl;

import com.ims.CommonUtil;
import com.ims.dto.UserDTO;
import com.ims.entity.MerchantManagerEntity;
import com.ims.entity.MerchantVendorEntity;
import com.ims.entity.UserEntity;
import com.ims.repository.MerchantManagerRepository;
import com.ims.repository.MerchantVendorRepository;
import com.ims.repository.UserRepository;
import com.ims.service.MerchantService;
import com.ims.service.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantManagerRepository merchantManagerRepository;
    @Autowired
    private MerchantVendorRepository merchantVendorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;

    @Override
    public List<UserDTO> getMerchantManagers() {
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        List<MerchantManagerEntity> mmeList = merchantManagerRepository.findAllByMerchantId(userDetails.getId());
        List<UserDTO> managers = new ArrayList<>();
        UserDTO userDTO = null;
        for(MerchantManagerEntity mme: mmeList){
            UserEntity ue = userRepository.findById(mme.getManagerId()).get();
            userDTO = new UserDTO();
            BeanUtils.copyProperties(ue, userDTO);
            managers.add(userDTO);
        }
        return managers;
    }

    @Override
    public List<UserDTO> getMerchantVendors() {
        UserDetailsImpl userDetails = commonUtil.loggedInUser();
        List<MerchantVendorEntity> mveList = merchantVendorRepository.findAllByVendorId(userDetails.getId());
        List<UserDTO> vendors = new ArrayList<>();
        UserDTO userDTO = null;
        for(MerchantVendorEntity mve: mveList){
            UserEntity ue = userRepository.findById(mve.getVendorId()).get();
            userDTO = new UserDTO();
            BeanUtils.copyProperties(ue, userDTO);
            vendors.add(userDTO);
        }
        return vendors;
    }
}
