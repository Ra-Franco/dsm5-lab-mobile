package com.social.media.controller;

import com.social.media.domain.user_profile.UserProfile;
import com.social.media.domain.user_profile.dto.UserProfileDto;
import com.social.media.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("id") Long id) {
        UserProfileDto user = userProfileService.getUserProfile(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserProfileDto> createUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileDto dto
            ){
        UserProfileDto userProfile = userProfileService.create(userDetails.getUsername(), dto);
        return ResponseEntity.ok(userProfile);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileDto dto
    ) {
        //TODO
        return ResponseEntity.ok().build();
    }

}
