package com.demo.DTO;

import java.util.List;

public class UserDTO {
	private String username;
	private String email;
	private List<String> roles;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String username, String email, List<String> roles) {
		super();
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
