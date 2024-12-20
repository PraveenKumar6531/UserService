package com.praveen.UserService.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
  @Query("select count(*) from Token token inner join User user on token.user.id = user.id and user.id = ?1 and token.deleted = false" )
  int findTokensByUser(Long userId);

  void removeTokenByValue(String value);

  void deleteTokenByValue(String token);

  Optional<Token> findTokenByValueAndDeleted(String token, boolean deleted);

  Optional<Token> findTokenByValueAndDeletedAndExpiryAtGreaterThan(String token, boolean deleted, Date currentTime);
}