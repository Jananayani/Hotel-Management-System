package com.example.auth_service.controller;

import com.example.auth_service.dto.AuthRequest;
import com.example.auth_service.dto.UserDto;
import com.example.auth_service.entity.User;
import com.example.auth_service.entity.Role;

import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.repository.RoleRepository;
import com.example.auth_service.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {


    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto, BindingResult result) {

        // Check if there are validation errors
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
//        User user1 = userRepository.findByUsername(userDto.getUsername());
        Optional<User> user1 = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));
        System.out.println("user1"+user1);
        if(!(user1.equals(Optional.empty()))){
            return ResponseEntity.ok("username already registered");

        }
        // If no validation errors, proceed with saving the user
        Optional<Role> role = roleRepository.findById(userDto.getRoleId());

        if(role.equals(Optional.empty())){
            return ResponseEntity.ok("Role not found");

        }
        Role exsistingRole = role.get();

        // Create a new user and set the role
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setRoleId(exsistingRole);  // Assign the role object to the user

        // Save user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest user, BindingResult result) {
        try {
            // Check if there are validation errors
            if (result.hasErrors()) {
                String errorMessage = result.getFieldError().getDefaultMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUsername());
                return ResponseEntity.ok("Successfully Logged" + token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }


}
