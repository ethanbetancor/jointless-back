package com.example.demo.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;



@Entity

public record User(
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    long id,	    
	    @Email
	    String email,
	    String username,
	    String password
) {}
