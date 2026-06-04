package com.example.demo.ui.controller.keys;


import com.example.demo.ui.dtos.keys.PublicKeyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/keys")
public class KeysController {
    private final KeySubController keySubController;
    
    KeysController(KeySubController keySubController) {
		this.keySubController = keySubController;
	}
    
    @GetMapping("/public")
    public ResponseEntity<PublicKeyResponse> publicKey() {
        return keySubController.getPublicKey();
    }
}
