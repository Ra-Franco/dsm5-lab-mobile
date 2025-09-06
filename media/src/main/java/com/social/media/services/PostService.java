package com.social.media.services;

import com.social.media.domain.posts.Post;
import com.social.media.domain.posts.dto.CreatePostDto;
import com.social.media.domain.posts.dto.ResponsePostDto;
import com.social.media.domain.user.User;
import com.social.media.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;


    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public ResponsePostDto create(CreatePostDto dto,String username) {
        User user = this.userService.findByUsernameOrThrow(username);
        return getPostDto(this.postRepository.save(new Post(dto.text(), dto.imageUrl(), user)));
    }

    private ResponsePostDto getPostDto(Post post) {
        return new ResponsePostDto(post.getText(),post.getImageUrl(), post.getCreatedAt(), post.getUser().getId());
    }
}
