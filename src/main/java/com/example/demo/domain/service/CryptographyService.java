package com.example.demo.domain.service;

import com.example.demo.domain.exceptions.EncryptionException;
import com.example.demo.domain.security.KeyManager;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class CryptographyService {

    private KeyManager keyManager;

    public CryptographyService(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public String decrypt(String cryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyManager.getRSAprivateKey());
            return new String(cipher.doFinal(Base64.getDecoder().decode(cryptedText)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidKeySpecException e) {
            throw new EncryptionException(e.getMessage());
        }
    }


}
