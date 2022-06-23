package com.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.models.ERole;
import com.demo.models.Role;
import com.demo.models.User;
import com.demo.payload.request.LoginRequest;
import com.demo.payload.request.SignupRequest;
import com.demo.payload.respone.JwtRespone;
import com.demo.payload.respone.MessageRespone;
import com.demo.repository.RoleRepository;
import com.demo.repository.UserRepository;
import com.demo.security.jwt.JwtUtils;
import com.demo.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	  @Autowired
	  AuthenticationManager authenticationManager;

	  @Autowired
	  UserRepository userRepository;
	
	  @Autowired
	  RoleRepository roleRepository;
	
	  @Autowired
	  PasswordEncoder encoder;
	
	  @Autowired
	  JwtUtils jwtUtils;
	
	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	
	    
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.gennerateJwtToken(authentication);
	    
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	
	    return ResponseEntity.ok(new JwtRespone(jwt, 
	                         userDetails.getId(), 
	                         userDetails.getUsername(), 
	                         userDetails.getEmail(), 
	                         roles));
	  }
	
	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageRespone("Error: Username is already taken!"));
	    }
	
	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity
	          .badRequest()
	          .body(new MessageRespone("Error: Email is already in use!"));
	    }
	
	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(), 
	               signUpRequest.getEmail(),
	               new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
	
	    Set<String> strRoles = signUpRequest.getRoles();
	    Set<Role> roles = new HashSet<>();
	    
	 	
	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_USER);
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {
	        switch (role) {
	        case "admin":
	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
	          roles.add(adminRole);
	
	          break;
	          
	        default:
	          Role userRole = roleRepository.findByName(ERole.ROLE_USER);
	          roles.add(userRole);
	        }
	      });
	    }
	
	    user.setRoles(roles);
	    userRepository.save(user);
	
	    return ResponseEntity.ok(new MessageRespone("User registered successfully!"));
	  }
}
