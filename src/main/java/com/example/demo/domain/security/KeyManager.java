package com.example.demo.domain.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import com.example.demo.data.KeysRepository;
import com.example.demo.domain.entities.Keys;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class KeyManager {

	private final KeysRepository keysRepository;

    public KeyManager(KeysRepository keysRepository) {
        this.keysRepository = keysRepository;
    }


    @PostConstruct
	public void init() {
		try {
			keysRepository.deleteAll();
			generate();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error in the process of creating the pair of keys");

		}
	}
	
	private void generate() throws NoSuchAlgorithmException {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair pair = generator.generateKeyPair();
			keysRepository.save(new Keys(0,
					Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()),
					Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded())));

	}

	public String getRSApublicKey() {
		return keysRepository.findAll().getFirst().getPublicKey();
    }

	public String getRSAprivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		return keysRepository.findAll().getFirst().getPrivateKey();
	}
	
}
