package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.models.User;
import com.demo.payload.request.ChangePasswordRequest;
import com.demo.payload.respone.MessageRespone;
import com.demo.repository.UserRepository;
import com.demo.service.impl.UserServiceImpl;

@Service
public class UserService implements UserServiceImpl{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public MessageRespone changePassword(ChangePasswordRequest changePassRequest) {
		
		User user = userRepository.findByEmail(changePassRequest.getEmail());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (user == null)
			return new MessageRespone("Email is incorect");
		
		if (!passwordEncoder.matches(changePassRequest.getOldPassword(), user.getPassword()))
			return new MessageRespone("Old password is incorect");
		
		user.setPassword(passwordEncoder.encode(changePassRequest.getNewPassword()));
		userRepository.save(user);
		
		return new MessageRespone("Password is changed");
		
	}

}
