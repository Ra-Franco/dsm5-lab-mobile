package com.social.media.services;

import com.social.media.domain.posts.images.PostImages;
import com.social.media.domain.posts.images.dto.PostImagesResponseDto;
import com.social.media.exception.ResourceNotFoundException;
import com.social.media.repository.PostImageRepository;
import com.social.media.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    public PostImageService(PostImageRepository postImageRepository, PostRepository postRepository) {
        this.postImageRepository = postImageRepository;
        this.postRepository = postRepository;
    }

    public List<PostImagesResponseDto> saveImage(Long postId, List<MultipartFile> files) throws IOException {
        List<PostImagesResponseDto> allImages = new ArrayList<>();
        for (MultipartFile file : files) {
            PostImages postImages = new PostImages();
            if (postRepository.findById(postId).isPresent()) {
                postImages.setPost( postRepository.findById(postId).get());
            } else {
                throw new ResourceNotFoundException("Post not found");
            }
            postImages.setImage(file.getBytes());
             allImages.add(PostImagesResponseDto.fromEntity(postImageRepository.save(postImages)));

        }
        return allImages;
    }

    public List<byte[]> getAllImages(Long postId){
        List<PostImages> imagesList =  postImageRepository.findByPostIdAll(postId);
        return imagesList.stream().map(PostImages::getImage).collect(Collectors.toList());
    }
}
