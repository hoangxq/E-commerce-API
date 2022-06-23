package com.demo.service.impl;

import com.demo.payload.request.ChangePasswordRequest;
import com.demo.payload.respone.MessageRespone;

public interface UserServiceImpl {

	MessageRespone changePassword(ChangePasswordRequest changePassRequest);
	
}
