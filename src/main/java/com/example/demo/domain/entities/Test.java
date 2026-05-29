package com.example.demo.domain.entities;

import jakarta.persistence.*;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    private String testPath;

    public Test() {}

    public Test(Level level, String testPath) {
        this.level = level;
        this.testPath = testPath;
    }

    public long getId() { return id; }
    public String getTestPath() { return testPath; }

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
    
    
}
