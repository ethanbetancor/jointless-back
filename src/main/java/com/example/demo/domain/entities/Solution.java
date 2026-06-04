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

    @Column(columnDefinition = "TEXT")
    private String code;

    private boolean passed;

    public Solution() {}

    public Solution(Level level, User user, String code, boolean passed) {
        this.level = level;
        this.user = user;
        this.code = code;
        this.passed = passed;
    }

	public long getId() {
		return id;
	}

	public Level getLevel() {
		return level;
	}

	public User getUser() {
		return user;
	}

	public String getCode() {
		return code;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
}
