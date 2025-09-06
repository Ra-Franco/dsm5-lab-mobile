package com.social.media.controller;

import com.social.media.domain.posts.dto.CreatePostDto;
import com.social.media.domain.posts.dto.ResponsePostDto;
import com.social.media.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<ResponsePostDto> createPost(
            @RequestBody CreatePostDto dto,
            @AuthenticationPrincipal UserDetails user
            ){
        ResponsePostDto response = this.postService.create(dto, user.getUsername());
        return ResponseEntity.status(201).body(response);
    }

}
