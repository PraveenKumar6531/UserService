package com.praveen.UserService.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {
  @Query("select count(*) from Token token inner join User user on token.user.id = user.id and user.id = ?1" )
  int findTokensByUser(Long userId);

  void removeTokenByValue(String value);

  void deleteTokenByValue(String token);

  Token findTokenByValue(String token);
}