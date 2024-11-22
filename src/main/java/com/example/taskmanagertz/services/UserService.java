package com.example.taskmanagertz.services;

import com.example.taskmanagertz.domain.task.Task;
import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.repository.UserRepository;
import com.example.taskmanagertz.web.dto.responses.user.UserResponse;
import com.example.taskmanagertz.web.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserResponse getUser(User user){
        Optional<User> userEntity = userRepository.findById(user.getId());
        if(userEntity.isPresent()){
            return new UserResponse(
                    user.getId(), user.getEmail(), user.getRole().name());
        } else {
            throw new NotFoundException("User not found");
        }
    }

}
