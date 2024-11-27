package com.praveen.UserService.services;

import com.praveen.UserService.models.TokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.praveen.UserService.models.Token;
import com.praveen.UserService.models.User;
import com.praveen.UserService.repositories.UserServiceRepository;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

@Service
public class UserService {
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserServiceRepository userServiceRepository;
	private TokenRepository tokenRepository;

	UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceRepository userServiceRepository,
				TokenRepository tokenRepository){
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userServiceRepository = userServiceRepository;
		this.tokenRepository = tokenRepository;
	}
	public User signup(String email, String name, String password) {
		
		User user = new User();
		user.setEmail(email);
		user.setName(name);		
		user.setHashedPassword(bCryptPasswordEncoder.encode(password));
		user.setEmailVerified(true);
		
		User savedUser = userServiceRepository.save(user);
		
		return savedUser;
	}
	
	public Token login(String email, String password) {
		User user = userServiceRepository.findUserByEmail(email);
		boolean isMatched = bCryptPasswordEncoder.matches(password, user.getHashedPassword());
		Token token = new Token();
		if(isMatched){
			int tokenCount = tokenRepository.findTokensByUser(user.getId());
			if(tokenCount < 3){
				token.setUser(user);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, 30);
				token.setExpiryAt(c.getTime());
				token.setValue(user.getName() + " - " +String.valueOf(System.currentTimeMillis()));
				tokenRepository.save(token);
				return token;
			}
			token.setValue("Exceeded the no. of logins!!!");
		}
		return token;
	}
	
	public void logout(String token) {
		Token tokenObj = tokenRepository.findTokenByValue(token);
		if(tokenObj != null) {
			tokenRepository.delete(tokenObj);
		}
		return;
	}
	
	public User validateToken(String token) {
		return null;
	}

}
