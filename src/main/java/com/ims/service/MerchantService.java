package com.ims.service;

import com.ims.dto.UserDTO;

import java.util.List;

public interface MerchantService {

    List<UserDTO> getMerchantManagers();
    List<UserDTO> getMerchantVendors();
}
