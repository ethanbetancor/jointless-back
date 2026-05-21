package com.example.demo.ui.controller;

import com.example.demo.data.KeysRepository;
import com.example.demo.domain.security.KeyManager;
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
    private final KeyManager keyManager;

    @GetMapping("/public")
    public ResponseEntity<PublicKeyResponse> publicKey() {
        return ResponseEntity.ok(new PublicKeyResponse(keyManager.getRSApublicKey().toString()));
    }
}
