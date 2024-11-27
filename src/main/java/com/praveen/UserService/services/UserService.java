package com.praveen.UserService.services;

import com.praveen.UserService.models.TokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.praveen.UserService.models.Token;
import com.praveen.UserService.models.User;
import com.praveen.UserService.repositories.UserServiceRepository;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;
import java.util.random.RandomGenerator;

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
				token = generateToken(user);
				tokenRepository.save(token);
				return token;
			}
			token.setValue("Exceeded the no. of logins!!!");
		}
		return token;
	}
	
	public void logout(String token) {
		Optional<Token> tokenObj = tokenRepository.findTokenByValueAndDeleted(token,false);
		if(tokenObj.isPresent()) {
			Token newToken = tokenObj.get();
			newToken.setDeleted(true);
			tokenRepository.save(newToken);
		}
		return;
	}

	private Token generateToken(User user) {
		Token token = new Token();
		LocalDate currentDate = LocalDate.now();
		LocalDate futureDate = currentDate.plusDays(30);
		token.setExpiryAt(Date.from(futureDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		token.setValue(RandomStringUtils.randomAlphanumeric(128));
		token.setUser(user);
		return token;
	}
	
	public User validateToken(String token) {
		Optional<Token> optionalUser = tokenRepository.findTokenByValueAndDeletedAndExpiryAtGreaterThan(token,false, new Date());
		if(optionalUser.isEmpty()) {
			return null;
		}
		return optionalUser.get().getUser();
	}

}
