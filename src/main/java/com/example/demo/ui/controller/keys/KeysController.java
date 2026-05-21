package com.example.demo.ui.controller.keys;


import com.example.demo.ui.dtos.keys.PublicKeyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keys")
@RequiredArgsConstructor
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
