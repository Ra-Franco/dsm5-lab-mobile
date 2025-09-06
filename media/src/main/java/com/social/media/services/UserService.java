package com.social.media.services;

import com.social.media.domain.user.User;
import com.social.media.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsernameOrThrow(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not Found"));
    }
}
