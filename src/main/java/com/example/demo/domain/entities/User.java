package com.example.demo.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Email
	private String email;

	private String username;

	private String password;

	public User() {}

	public User(long id, String email, String username, String password) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
