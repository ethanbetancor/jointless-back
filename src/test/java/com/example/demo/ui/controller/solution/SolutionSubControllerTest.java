package com.example.demo.ui.controller.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.controller.Solution.SolutionSubController;
import com.example.demo.ui.dtos.solution.SubmitResponse;

@ExtendWith(MockitoExtension.class)
public class SolutionSubControllerTest {
	 	@Mock
	    private SolutionService solutionService;

	    @Mock
	    private CredentialsValidator credentialsValidator;

	    @InjectMocks
	    private SolutionSubController controller;
	    
	    @Test
	    void shouldReturnBadRequestWhenInputIsInvalid() {

	        ResponseEntity<SubmitResponse> response =
	                controller.submit("", 1L, "");

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        
	        response = controller.submit(null, null, null);
	        
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        
	        verify(solutionService, times(0))
            	.submit(anyString(), anyLong(), anyLong());
	    }
	    
	    @Test
	    void shouldReturnUnauthorizedWhenUserInvalid() {

	        when(credentialsValidator.check(anyString()))
	                .thenReturn(Optional.empty());

	        ResponseEntity<SubmitResponse> response =
	                controller.submit("code", 1L, "cred");

	        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

	        verify(solutionService, times(0))
	                .submit(anyString(), anyLong(), anyLong());
	    }
	    
	    @Test
	    void shouldReturnOkWhenSubmitIsSuccessful() {

	        User user = new User();
	        user.setId(1L);

	        when(credentialsValidator.check(anyString()))
	                .thenReturn(Optional.of(user));

	        when(solutionService.submit(anyString(), anyLong(), anyLong()))
	                .thenReturn(new SubmitResponse("ok", true));

	        ResponseEntity<SubmitResponse> response =
	                controller.submit("code", 1L, "cred");

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertTrue(response.getBody().success());

	        verify(solutionService, times(1))
	                .submit(anyString(), anyLong(), anyLong());
	    }
}
