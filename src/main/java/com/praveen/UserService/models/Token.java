package com.praveen.UserService.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tokens")
public class Token extends BaseModel{
	private String value;
	@ManyToOne
	private User user;
	private Date expiryAt;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getExpiryAt() {
		return expiryAt;
	}
	public void setExpiryAt(Date expiryAt) {
		this.expiryAt = expiryAt;
	}
}
