package com.ims.controller;

import com.ims.service.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Log4j2
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('VOLUNTEER') or hasRole('OWNER') or hasRole('EXHIBITOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "All User Content Except VISITOR";
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public String moderatorAccess() {
        return "OWNER Board.";
    }

    @GetMapping("/exhibitor")
    @PreAuthorize("hasRole('EXHIBITOR')")
    public String exhibitorAccess() {
        return "EXHIBITOR Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/visitor")
    @PreAuthorize("hasRole('VISITOR')")
    public String visitorAccess() {
        return "VISITOR Board.";
    }

    @GetMapping("/volunteer")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public String volunteerAccess() {
        return "VOLUNTEER Board.";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('MANAGER') or hasRole('MERCHANT') or hasRole('VENDOR') or hasRole('ADMIN')")
    public UserDetailsImpl profile() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("username: {}", userDetails.getUsername());
        return userDetails;
    }
}
