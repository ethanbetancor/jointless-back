package com.example.demo.ui.controller.groq;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.service.GroqService;
import com.example.demo.ui.dtos.groq.*;

@ExtendWith(MockitoExtension.class)
class GroqSubControllerTest {

    @Mock
    private GroqService groqService;

    @InjectMocks
    private GroqSubController subController;

    @Test
    void answerClue_ok() {

        ApiRequest request = new ApiRequest("hola");

        GroqResponse mockResponse = new GroqResponse(
                List.of(new GroqResponse.Choice(
                        new GroqMessage("assistant", "respuesta")
                ))
        );

        when(groqService.askGroqForClue("hola")).thenReturn(mockResponse);

        ResponseEntity<ApiResponse> result = subController.answerClue(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("respuesta", result.getBody().clue());
    }

    @Test
    void answerClue_nullResponse() {

        ApiRequest request = new ApiRequest("hola");

        when(groqService.askGroqForClue("hola")).thenReturn(null);

        ResponseEntity<ApiResponse> result = subController.answerClue(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void answerClue_emptyChoices() {

        ApiRequest request = new ApiRequest("hola");

        GroqResponse mockResponse = new GroqResponse(List.of());

        when(groqService.askGroqForClue("hola")).thenReturn(mockResponse);

        ResponseEntity<ApiResponse> result = subController.answerClue(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void answerClue_verifiesServiceCall() {

        ApiRequest request = new ApiRequest("test");

        GroqResponse mockResponse = new GroqResponse(
                List.of(new GroqResponse.Choice(
                        new GroqMessage("assistant", "ok")
                ))
        );

        when(groqService.askGroqForClue("test")).thenReturn(mockResponse);

        subController.answerClue(request);

        verify(groqService, times(1)).askGroqForClue("test");
    }
}