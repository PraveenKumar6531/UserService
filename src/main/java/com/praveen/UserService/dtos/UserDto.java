package com.praveen.UserService.dtos;

import java.util.List;

import com.praveen.UserService.models.Role;
import com.praveen.UserService.models.User;

public class UserDto {
	private String name;
	private String email;
	private List<Role> roles;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public static UserDto from(User user) {
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setRoles(user.getRoles());
		
		return userDto;
	}
}
