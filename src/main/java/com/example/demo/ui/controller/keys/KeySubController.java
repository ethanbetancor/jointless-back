package com.example.demo.ui.controller.keys;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.domain.security.KeyManager;
import com.example.demo.ui.dtos.keys.PublicKeyResponse;

@Component
class KeySubController {
	private KeyManager keyManager;
	
	public KeySubController(KeyManager keyManager) {
		this.keyManager=keyManager;
	}
	
	ResponseEntity<PublicKeyResponse> getPublicKey(){
		if(keyManager.getRSApublicKey().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok().body(new PublicKeyResponse(keyManager.getRSApublicKey()));

	}
}
