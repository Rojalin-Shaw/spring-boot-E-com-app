package com.example.main.controller;

import java.io.IOException;
import java.util.Optional;

import com.example.main.entity.User;  // Correct import

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.main.dto.AuthenticationRequest;
import com.example.main.dto.SignupRequest;
import com.example.main.dto.UserDto;
import com.example.main.repository.UserRepository;
import com.example.main.services.auth.AuthService;
import com.example.main.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager = null;
    private final UserDetailsService userDetailsService = null;
    private final UserRepository userRepository = null;
    private final JwtUtil jwtUtil = new JwtUtil();

    public static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";  // Proper header string
    
    private final AuthService authService = null;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString());
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        }
    }
    
    @PostMapping("/sign-up")
    
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
    	if(authService.hasUserWithEmail(signupRequest.getEmail())) {
    		return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
    	}
    	UserDto userDto = authService.createUser(signupRequest);
    	return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
