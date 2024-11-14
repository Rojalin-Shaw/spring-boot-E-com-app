package com.example.main.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.main.dto.SignupRequest;
import com.example.main.dto.UserDto;
import com.example.main.entity.User;
import com.example.main.enums.UserRole;
import com.example.main.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceimpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();

        // Set properties for the User entity
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER); // Default role is set to CUSTOMER

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Convert the saved User entity to a UserDto
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId()); // Get Long id from User entity
        userDto.setEmail(savedUser.getEmail());
        userDto.setName(savedUser.getName());
        userDto.setUserRole(savedUser.getRole());

        return userDto;
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
    
    @PostConstruct
    public void createAdminAccount() {
    	User adminAccount = userRepository.findByRole(UserRole.ADMIN);
    	if(null == adminAccount) {
    		User user = new User();
    		 user.setEmail("admin@test.com");
    		user.setName("admin");
    		user.setRole(UserRole.ADMIN);
    		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
    		userRepository.save(user);
    		

    	}
    }
}
