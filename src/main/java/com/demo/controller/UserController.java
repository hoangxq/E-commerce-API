package com.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.DTO.UserDTO;
import com.demo.models.User;
import com.demo.payload.request.ChangePasswordRequest;
import com.demo.payload.respone.MessageRespone;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() {
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> getUserByUsername(@Valid @RequestBody UserDTO userRequest) {
		User user = userRepository.findByUsername(userRequest.getUsername());
		
		if (user == null) 
			return ResponseEntity.ok(new MessageRespone("user not found"));
		
		return ResponseEntity.ok(user.toDTO());
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePassRequest){
		
		return ResponseEntity.ok(userService.changePassword(changePassRequest));
		
	}
}
