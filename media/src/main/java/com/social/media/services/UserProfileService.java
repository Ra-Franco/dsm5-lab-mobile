package com.social.media.services;

import com.social.media.domain.user.User;
import com.social.media.domain.user_profile.UserProfile;
import com.social.media.domain.user_profile.dto.UserProfileDto;
import com.social.media.repository.UserProfileRepository;
import com.social.media.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public UserProfileDto create(String username, UserProfileDto dto){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            UserProfile userProfile = this.userProfileRepository.save(new UserProfile(user.get(), dto.bio(), dto.fullname(), dto.profileImageUrl()));
            return this.createDto(userProfile);
        } else {
            throw new EntityNotFoundException("User not found");
        }

    }

    public UserProfileDto getUserProfile(Long id){
        Optional<UserProfile> user = userProfileRepository.findById(id);
        if(user.isPresent()){
            return this.createDto(user.get());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public UserProfileDto updateUserProfile(Long id, UserProfileDto dto, UserDetails userDetails){
        Optional<UserProfile> user = userProfileRepository.findById(id);
        if(user.isPresent()){
            UserProfile userProfile = user.get();
            if (!userDetails.getUsername().equals(userProfile.getUser().getUsername())) throw new RuntimeException("User not allowed to update profile");
            userProfile.setBio(dto.bio());
            userProfile.setFullName(dto.fullname());
            userProfile.setProfilePicture(dto.profileImageUrl());
            return this.createDto(userProfileRepository.save(userProfile));
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    private UserProfileDto createDto(UserProfile userProfile){
        return new UserProfileDto(userProfile.getFullName(), userProfile.getBio(), userProfile.getProfilePicture());
    }

}
