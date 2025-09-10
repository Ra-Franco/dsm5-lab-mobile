package com.social.media.controller;

import com.social.media.domain.posts.dto.CreatePostDto;
import com.social.media.domain.posts.dto.ResponsePostDto;
import com.social.media.domain.posts.images.dto.PostImagesResponseDto;
import com.social.media.services.LikeService;
import com.social.media.services.PostImageService;
import com.social.media.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final PostImageService postImageService;

    public PostController(PostService postService, LikeService likeService, PostImageService postImageService) {
        this.postService = postService;
        this.likeService = likeService;
        this.postImageService = postImageService;
    }


    @PostMapping
    public ResponseEntity<ResponsePostDto> createPost(
            @RequestBody CreatePostDto dto,
            @AuthenticationPrincipal UserDetails user
            ){
        ResponsePostDto response = this.postService.create(dto, user.getUsername());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePostDto>> getPost(
            @AuthenticationPrincipal UserDetails user
    ){
        return ResponseEntity.ok(this.postService.getAllPosts(user.getUsername()));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(
            @PathVariable("id") Long postId,
            @AuthenticationPrincipal UserDetails user
    ){
        this.likeService.likePost(postId, user.getUsername());
        return ResponseEntity.ok("Liked post " + postId);
    }

    @DeleteMapping("/{id}/unlike")
    public ResponseEntity<?> unlikePost(
            @PathVariable("id") Long postId,
            @AuthenticationPrincipal UserDetails user
    ){
        return ResponseEntity.ok(this.likeService.unlikePost(user.getUsername(), postId));
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<List<PostImagesResponseDto>> uploadPostImage(
            @PathVariable("id") Long postId,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        List<PostImagesResponseDto> images = this.postImageService.saveImage(postId, files);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<byte[]>> getPostImages(
            @PathVariable("id") Long postId
    ){
      return ResponseEntity.ok(this.postImageService.getAllImages(postId));
    }

    //TODO /{id}/images/{image_id}

}
