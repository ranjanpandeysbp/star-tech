package com.ims.controller;

import com.ims.CommonUtil;
import com.ims.dto.AuthRequestDTO;
import com.ims.dto.AuthResponseDTO;
import com.ims.dto.BrandDTO;
import com.ims.entity.*;
import com.ims.repository.ContractRepository;
import com.ims.repository.QuoteRepository;
import com.ims.repository.RoleRepository;
import com.ims.repository.UserRepository;
import com.ims.service.JwtService;
import com.ims.service.UserDetailsImpl;
import com.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/contract")
public class ContractController {
@Autowired
private ContractRepository contractRepository;
 @Autowired
 private UserRepository userRepository;
@Autowired
private CommonUtil commonUtil;

 @GetMapping
 public ResponseEntity<List<ContractEntity>> getByMerchantId() {
  List<ContractEntity> contractEntityList = contractRepository.findAll();
  return new ResponseEntity<>(contractEntityList, HttpStatus.OK);
 }
 }
