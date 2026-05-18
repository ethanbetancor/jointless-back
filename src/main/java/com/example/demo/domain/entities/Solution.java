package com.example.demo.domain.entities;

import jakarta.persistence.*;

@Entity
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String code;
    private boolean passed;
}
