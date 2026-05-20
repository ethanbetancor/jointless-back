package com.example.demo.domain.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Keys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(columnDefinition = "TEXT")
    private String publicKey;

	@Column(columnDefinition = "TEXT")
    private String privateKey;
	
	public Keys() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}