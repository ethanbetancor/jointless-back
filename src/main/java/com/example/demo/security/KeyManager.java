package com.example.demo.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class KeyManager {
	
	private PublicKey RSApublicKey;
	private PrivateKey RSAprivateKey;
	
	@PostConstruct
	public void init() {
		try {
			generate();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error in the process of creating the pair of keys");

		}
	}
	
	private void generate() throws NoSuchAlgorithmException {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");	
			generator.initialize(2048);
			KeyPair pair = generator.generateKeyPair();
			this.RSAprivateKey = pair.getPrivate();
			this.RSApublicKey = pair.getPublic();
	}

	public PublicKey getRSApublicKey() {
		return RSApublicKey;
	}

	public PrivateKey getRSAprivateKey() {
		return RSAprivateKey;
	}
	
}
