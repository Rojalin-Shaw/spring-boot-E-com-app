package com.example.main.dto;

import com.example.main.enums.UserRole;

import lombok.Data;

@Data

public class UserDto {

	private Long id;
	
	private String  email;
	
	private String name;
	
	private UserRole userRole;

	public Long getId() {
		return id;
	}

	public void setId(Long createdUser) {
		this.id = createdUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


	
}
