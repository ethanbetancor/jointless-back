package com.example.demo;

import com.example.demo.data.KeysRepository;
import com.example.demo.domain.entities.Keys;
import com.example.demo.domain.security.KeyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeyManagerTest {

    @Mock
    private KeysRepository keysRepository;

    private KeyManager keyManager;

    @BeforeEach
    void setUp() {
        keyManager = new KeyManager(keysRepository);
    }
    private Keys buildRealKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair pair = gen.generateKeyPair();
        String pub  = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String priv = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
        return new Keys(1L, pub, priv);
    }

    @Test
    void init_deletesAllAndSavesNewKeys() {
        keyManager.init();

        verify(keysRepository).deleteAll();
        ArgumentCaptor<Keys> captor = ArgumentCaptor.forClass(Keys.class);
        verify(keysRepository).save(captor.capture());
        Keys saved = captor.getValue();
        assertThat(saved.getPublicKey()).isNotBlank();
        assertThat(saved.getPrivateKey()).isNotBlank();
    }

    @Test
    void getRSApublicKey_returnsPublicKeyString() throws NoSuchAlgorithmException {
        Keys keys = buildRealKeys();
        when(keysRepository.findAll()).thenReturn(List.of(keys));

        String result = keyManager.getRSApublicKey();

        assertThat(result).isEqualTo(keys.getPublicKey());
    }

    @Test
    void getRSAprivateKey_returnsDecodedPrivateKey() throws Exception {
        Keys keys = buildRealKeys();
        when(keysRepository.findAll()).thenReturn(List.of(keys));

        PrivateKey result = keyManager.getRSAprivateKey();

        assertThat(result).isNotNull();
        assertThat(result.getAlgorithm()).isEqualTo("RSA");
    }

    @Test
    void getRSAprivateKey_withInvalidBase64_throwsException() {
        Keys badKeys = new Keys(1L, "pubkey", "a");
        when(keysRepository.findAll()).thenReturn(List.of(badKeys));

        assertThatThrownBy(() -> keyManager.getRSAprivateKey())
                .isInstanceOf(Exception.class);
    }
}
