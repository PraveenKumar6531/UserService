package com.praveen.UserService.controllers;

import com.praveen.UserService.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praveen.UserService.dtos.LoginRequestDto;
import com.praveen.UserService.dtos.SignupRequestDto;
import com.praveen.UserService.dtos.UserDto;
import com.praveen.UserService.models.Token;
import com.praveen.UserService.models.User;
import com.praveen.UserService.services.UserService;

@RestController
@RequestMapping("/users")
public class UserServiceController {
	UserService userService;
	
	UserServiceController(UserService userService){
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public UserDto signup(@RequestBody SignupRequestDto requestDto) {
		User user = userService.signup(requestDto.getEmail(), requestDto.getName(), requestDto.getPassword());
		return UserDto.from(user);
	}
	
	@PostMapping("/login")
	public Token login(@RequestBody LoginRequestDto requestDto) {
		Token token = userService.login(requestDto.getEmail(), requestDto.getPassword());
		if(token.getValue() == null) {
			throw new UserNotFoundException("User with email: "+requestDto.getEmail() +" does not exist");
		}
		return token;
	}
	
	@PostMapping("/logout/{token}")
	public ResponseEntity<Void> logout(@PathVariable String token) {
		userService.logout(token);
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);

		return responseEntity;
	}
	
	@GetMapping("/validate/{token}")
	public UserDto validateToken(@PathVariable String token) {
		return UserDto.from(userService.validateToken(token));
	}
	
	@GetMapping("/users/{id}")
	public UserDto getUserById(@PathVariable String id) {
		return null;
	}

}
