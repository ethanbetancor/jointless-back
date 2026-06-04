package com.example.demo.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private boolean isActive;

	public User() {}

	public User(long id, String email, String username, String password) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = Role.USER;
		this.isActive = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return this.password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
}
