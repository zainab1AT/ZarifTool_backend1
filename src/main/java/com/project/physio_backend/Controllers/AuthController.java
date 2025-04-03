package com.project.physio_backend.Controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Security.jwt.JwtUtils;
import com.project.physio_backend.Security.services.UserDetailsImpl;
import com.project.physio_backend.Services.UserService.UserService;
import com.project.physio_backend.payload.request.LoginRequest;
import com.project.physio_backend.payload.request.SignupRequest;
import com.project.physio_backend.payload.response.JwtResponse;
import com.project.physio_backend.payload.response.MessageResponse;
import com.project.physio_backend.Security.services.UserDetailsImpl;

import jakarta.validation.Valid;

// @CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    User user = new User(signUpRequest.getUsername(),
        passwordEncoder.encode(signUpRequest.getPassword()));

    User user2 = userService.createUser(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/google")
  public ResponseEntity<?> getLoginInfo(@RequestHeader("Authorization") String authorization,
      @RequestBody Map<String, String> userRequest) {
    try {
      String token = authorization.replace("Bearer ", "");
      String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;

      RestTemplate restTemplate = new RestTemplate();
      Map<String, Object> userInfo = restTemplate.getForObject(url, Map.class);

      if (userInfo == null || userInfo.containsKey("error")) {
        return ResponseEntity.badRequest().body("Invalid Google token");
      }
      String username = userRequest.get("username");

      Optional<User> optionalUser = userRepository.findByUsername(username);
      System.out.println(username);

      if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        userInfo.put("id", user.getUserID());
        userInfo.put("username", user.getUsername());
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        userInfo.put("accessToken", jwtToken);
      } else {
        User newUser = new User();
        newUser.setUsername(username);

        // Set a default or random password (won't be used for login)
        newUser.setPassword(passwordEncoder.encode("defaultPassword123"));

        User savedUser = userService.createUser(newUser);

        userInfo.put("id", savedUser.getUserID());
        userInfo.put("username", savedUser.getUsername());
        String jwtToken = jwtUtils.generateTokenFromUsername(savedUser.getUsername());
        userInfo.put("accessToken", jwtToken);
      }

      return ResponseEntity.ok(userInfo);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
  }

}