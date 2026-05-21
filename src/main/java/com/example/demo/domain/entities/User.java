package com.example.demo.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
<<<<<<< Updated upstream
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
>>>>>>> Stashed changes
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Email
    private String email;
    private String username;
    private String password;
    private boolean isAuthenticated;
}
