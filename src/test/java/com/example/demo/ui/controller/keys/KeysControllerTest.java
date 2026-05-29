package com.example.demo.ui.controller.keys;

import com.example.demo.domain.security.KeyManager;
import com.example.demo.ui.dtos.keys.PublicKeyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeysControllerTest {

    @Mock
    private KeyManager keyManager;

    @InjectMocks
    private KeySubController keySubController;


    @Test
    void getPublicKey_whenKeyExists_returns200WithKey() {
        when(keyManager.getRSApublicKey()).thenReturn("MIIBIjANBgkq...fakePublicKey==");

        ResponseEntity<PublicKeyResponse> response = keySubController.getPublicKey();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().publicKey()).isEqualTo("MIIBIjANBgkq...fakePublicKey==");
    }

    @Test
    void getPublicKey_whenKeyIsEmpty_returns404() {
        when(keyManager.getRSApublicKey()).thenReturn("");

        ResponseEntity<PublicKeyResponse> response = keySubController.getPublicKey();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }


    @Test
    void keysController_publicKey_delegatesToSubController() {
        KeySubController mockSub = mock(KeySubController.class);
        KeysController controller = new KeysController(mockSub);

        ResponseEntity<PublicKeyResponse> expected = ResponseEntity.ok(new PublicKeyResponse("someKey"));
        when(mockSub.getPublicKey()).thenReturn(expected);

        ResponseEntity<PublicKeyResponse> result = controller.publicKey();

        assertThat(result).isSameAs(expected);
        verify(mockSub).getPublicKey();
    }
}

