package com.social.media.controller;

import com.social.media.domain.user.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/profile")
public class UserProfileController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") String id) {
        return ResponseEntity.ok(id);
    }

}
