package com.example.main.services.auth;

import com.example.main.dto.SignupRequest;
import com.example.main.dto.UserDto;

public interface AuthService {

	 UserDto createUser(SignupRequest signupRequest);
	 
	 Boolean hasUserWithEmail(String email);
}
