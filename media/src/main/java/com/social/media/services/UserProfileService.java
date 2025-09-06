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
    private final UserService userService;

    public UserProfileService(UserProfileRepository userProfileRepository, UserService userService) {
        this.userProfileRepository = userProfileRepository;
        this.userService = userService;
    }

    public UserProfileDto create(String username, UserProfileDto dto){
        User user = userService.findByUsernameOrThrow(username);
            UserProfile userProfile = this.userProfileRepository.save(new UserProfile(user, dto.bio(), dto.fullname(), dto.profileImageUrl()));
            return this.createDto(userProfile);
    }

    public UserProfileDto getUserProfile(Long id){
        UserProfile userProfile = this.findUserProfileById(id);
            return this.createDto(userProfile);
    }

    public UserProfileDto updateUserProfile(Long id, UserProfileDto dto, String username){
        UserProfile userProfile = this.findUserProfileById(id);
        if (!username.equals(userProfile.getUser().getUsername())) throw new RuntimeException("User not allowed to update profile");
        userProfile.setBio(dto.bio());
        userProfile.setFullName(dto.fullname());
        userProfile.setProfilePicture(dto.profileImageUrl());
        return this.createDto(userProfileRepository.save(userProfile));
    }

    private UserProfile findUserProfileById(Long id){
        return this.userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private UserProfileDto createDto(UserProfile userProfile){
        return new UserProfileDto(userProfile.getFullName(), userProfile.getBio(), userProfile.getProfilePicture());
    }

}
