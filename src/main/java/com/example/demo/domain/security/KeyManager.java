package com.example.demo.domain.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.example.demo.data.KeysRepository;
import com.example.demo.domain.entities.Keys;
import com.example.demo.domain.exceptions.EncryptionException;
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

	public PublicKey getRSApublicKey() {
		String pubString = keysRepository.findAll().getFirst().getPublicKey();
		byte[] pubBytes = Base64.getDecoder().decode(pubString);
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pubBytes));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new EncryptionException(e.getMessage());
        }
    }

	public PrivateKey getRSAprivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String privString = keysRepository.findAll().getFirst().getPrivateKey();
		byte[] privBytes = Base64.getDecoder().decode(privString);
		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privBytes));
	}
	
}
