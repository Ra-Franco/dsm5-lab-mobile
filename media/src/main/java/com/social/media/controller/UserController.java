package com.social.media.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UserController {

    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(
            @RequestBody User user
    ) {
        URI uri = URI.create("/auth/" + user.getUsername());
        return ResponseEntity.created(uri).body(user.getUsername());
    }

}
