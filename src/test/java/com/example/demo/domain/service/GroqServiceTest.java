package com.example.demo.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import com.example.demo.domain.security.GroqManager;
import com.example.demo.ui.dtos.groq.*;

@ExtendWith(MockitoExtension.class)
class GroqServiceTest {
	
	@Mock
    private GroqManager groqManager;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec uriSpec;

    @Mock
    private RestClient.RequestBodySpec bodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private GroqService groqService;
    
    @BeforeEach
    void setUp() {

        when(groqManager.getModel()).thenReturn("model");
        when(groqManager.getClient()).thenReturn(restClient);

        when(restClient.post()).thenReturn(uriSpec);
        when(uriSpec.body(any(GroqRequest.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.body(GroqResponse.class))
                .thenReturn(new GroqResponse(List.of(
                        new GroqResponse.Choice(
                                new GroqMessage("assistant", "ok")
                        )
                )));
    }
    
    @Test
    void askGroqForClue_ok() {
        GroqResponse response = groqService.askGroqForClue("hola");

        assertNotNull(response);
    }

    @Test
    void askGroqForImprovement_ok() {
    	GroqResponse response = groqService.askGroqForImprovement("hola");

        assertNotNull(response);
    }
}