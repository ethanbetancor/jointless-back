package com.example.demo;

import com.example.demo.domain.exceptions.EncryptionException;
import com.example.demo.domain.security.KeyManager;
import com.example.demo.domain.service.CryptographyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptographyServiceTest {

    @Mock
    private KeyManager keyManager;

    private CryptographyService cryptographyService;

    private KeyPair keyPair;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        keyPair = gen.generateKeyPair();
        cryptographyService = new CryptographyService(keyManager);
    }

    private String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    @Test
    void decrypt_returnsOriginalPlainText() throws Exception {
        String original = "user@example.com:password123";
        String encrypted = encrypt(original);

        when(keyManager.getRSAprivateKey()).thenReturn(keyPair.getPrivate());

        String result = cryptographyService.decrypt(encrypted);

        assertThat(result).isEqualTo(original);
    }

    @Test
    void decrypt_withInvalidCipherText_throwsEncryptionException() throws Exception {
        when(keyManager.getRSAprivateKey()).thenReturn(keyPair.getPrivate());
        byte[] garbage = new byte[256];
        new java.util.Random().nextBytes(garbage);
        String badCipher = Base64.getEncoder().encodeToString(garbage);

        assertThatThrownBy(() -> cryptographyService.decrypt(badCipher))
                .isInstanceOf(EncryptionException.class);
    }

    @Test
    void decrypt_whenKeyManagerThrows_throwsEncryptionException() throws Exception {
        when(keyManager.getRSAprivateKey()).thenThrow(new NoSuchAlgorithmException("algo fallo"));

        assertThatThrownBy(() -> cryptographyService.decrypt("cualquiercosa"))
                .isInstanceOf(EncryptionException.class);
    }
}
