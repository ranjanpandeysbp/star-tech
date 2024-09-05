package com.ims.controller;

import com.ims.dto.UserDTO;
import com.ims.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping("/managers")
    public List<UserDTO> getMerchantManagers(){
        return merchantService.getMerchantManagers();
    }

    @GetMapping("/vendors")
    public List<UserDTO> getMerchantVendors(){
        return merchantService.getMerchantVendors();
    }
}
