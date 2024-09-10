package com.ims.controller;

import com.ims.CommonUtil;
import com.ims.dto.AuthRequestDTO;
import com.ims.dto.AuthResponseDTO;
import com.ims.entity.*;
import com.ims.repository.MerchantManagerRepository;
import com.ims.repository.MerchantVendorRepository;
import com.ims.repository.RoleRepository;
import com.ims.repository.UserRepository;
import com.ims.service.JwtService;
import com.ims.service.UserDetailsImpl;
import com.ims.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userDetailsService;
    private CommonUtil commonUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService, CommonUtil commonUtil,
                          MerchantManagerRepository merchantManagerRepository,
                          MerchantVendorRepository merchantVendorRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.commonUtil = commonUtil;
    }
    @GetMapping("/update-role/{userId}/{role}")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @PathVariable String role){
        Optional<UserEntity> optUser = userRepository.findById(userId);
        UserEntity ue = null;
         if(optUser.isPresent()){
             ue = optUser.get();
             RoleEntity re = new RoleEntity();
             if("ROLE_ADMIN".equals(role)) {
                 re.setName(ERole.ROLE_ADMIN);
                 Set<RoleEntity> sre = new HashSet<>();
                 sre.add(re);
                 ue.setRoles(sre);
             }else if("ROLE_MANAGER".equals(role)) {
                 re.setName(ERole.ROLE_MANAGER);
                 Set<RoleEntity> sre = new HashSet<>();
                 sre.add(re);
                 ue.setRoles(sre);
             }
             ue = userRepository.save(ue);
         }
        return ResponseEntity.ok(ue);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        String jwt = null;
        if (authentication.isAuthenticated()) {
            jwt = jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        AuthResponseDTO res = new AuthResponseDTO();
        res.setToken(jwt);
        res.setId(userDetails.getId());
        res.setFirstName(userDetails.getFirstName());
        res.setLastName(userDetails.getLastName());
        res.setRoles(roles);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthRequestDTO authRequestDTO) {
        if (userRepository.existsByEmail(authRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(authRequestDTO.getPassword());
        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> userRole = roleRepository.findByName(ERole.ROLE_MERCHANT);
        if (userRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());
        UserEntity user = new UserEntity();
        user.setFirstName(authRequestDTO.getFirstName());
        user.setLastName(authRequestDTO.getLastName());
        user.setEmail(authRequestDTO.getEmail());
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered success");
    }

    @PostMapping("/signup/merchant")
    public ResponseEntity<?> signupMerchant(@RequestBody AuthRequestDTO authRequestDTO) {
        if (userRepository.existsByEmail(authRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(authRequestDTO.getPassword());
        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> userRole = roleRepository.findByName(ERole.ROLE_MERCHANT);
        if (userRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());
        UserEntity user = new UserEntity();
        user.setFirstName(authRequestDTO.getFirstName());
        user.setLastName(authRequestDTO.getLastName());
        user.setEmail(authRequestDTO.getEmail());
        user.setDob(authRequestDTO.getDob());
        user.setPhone(authRequestDTO.getPhone());
        user.setNationality(authRequestDTO.getNationality());
        user.setGender(authRequestDTO.getGender());
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        user.setPassword(null);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/signup/lender")
    public ResponseEntity<?> signupLender(@RequestBody AuthRequestDTO authRequestDTO) {
        if (userRepository.existsByEmail(authRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(authRequestDTO.getPassword());
        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> userRole = roleRepository.findByName(ERole.ROLE_LENDER);
        if (userRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());
        UserEntity user = new UserEntity();
        user.setFirstName(authRequestDTO.getFirstName());
        user.setLastName(authRequestDTO.getLastName());
        user.setEmail(authRequestDTO.getEmail());
        user.setDob(authRequestDTO.getDob());
        user.setPhone(authRequestDTO.getPhone());
        user.setNationality(authRequestDTO.getNationality());
        user.setGender(authRequestDTO.getGender());
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody AuthRequestDTO authRequestDTO) {
        if (userRepository.existsByEmail(authRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email is already taken");
        }
        String hashedPassword = passwordEncoder.encode(authRequestDTO.getPassword());
        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> userRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        if (userRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("role not found");
        }
        roles.add(userRole.get());
        UserEntity user = new UserEntity();
        user.setFirstName(authRequestDTO.getFirstName());
        user.setLastName(authRequestDTO.getLastName());
        user.setEmail(authRequestDTO.getEmail());
        user.setDob(authRequestDTO.getDob());
        user.setPhone(authRequestDTO.getPhone());
        user.setNationality(authRequestDTO.getNationality());
        user.setGender(authRequestDTO.getGender());
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping("/current-user")
    public UserDetailsImpl getCurrentUser(Principal principal) {
        return commonUtil.loggedInUser();
    }
    @GetMapping("/user-by-email")
    public UserDetailsImpl getUserByEmail(Principal principal) {
        return commonUtil.loggedInUser();
    }
}
