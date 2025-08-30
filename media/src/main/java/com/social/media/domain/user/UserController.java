package com.social.media.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UserController {

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(
            @RequestBody User user
    ) {
        System.out.println(user.getUsername());
        URI uri = URI.create("/auth/users/" + user.getUsername());
        return ResponseEntity.created(uri).body(user.getUsername());
    }

}
