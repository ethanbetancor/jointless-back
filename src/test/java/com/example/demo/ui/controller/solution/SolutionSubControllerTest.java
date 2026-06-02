package com.example.demo.ui.controller.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.security.CredentialsValidator;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.ui.controller.Solution.SolutionSubController;
import com.example.demo.ui.dtos.solution.SubmitResponse;
import com.example.demo.ui.dtos.solution.*;

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
	    
	    
	    @Test
		void getByUser_BadRequestWhenCredentialsAreNullOrEmpty() {
			SolutionRequest nullRequest = new SolutionRequest(null);
			ResponseEntity<SolutionListResponse> responseNull = controller.getByUser(nullRequest);
			assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
			SolutionRequest emptyRequest = new SolutionRequest("");
			ResponseEntity<SolutionListResponse> responseEmpty = controller.getByUser(emptyRequest);
			assertEquals(HttpStatus.BAD_REQUEST, responseEmpty.getStatusCode());

		}

		@Test
		void getByUser_shouldReturnUnauthorized() {
			SolutionRequest request = new SolutionRequest("invalid_credentials");
			when(credentialsValidator.check(anyString())).thenReturn(Optional.empty());
			ResponseEntity<SolutionListResponse> response = controller.getByUser(request);
			assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		}

		@Test
		void getByUser_shouldReturnOk() {
			SolutionRequest request = new SolutionRequest("valid_credentials");
			
			User user = new User();
			user.setId(1L);

			Level level = new Level();
			level.setId(10L);

			Solution solution = new Solution();
			solution.setId(100L);
			solution.setLevel(level);
			solution.setUser(user);
			solution.setCode("print('Hello World')");
			solution.setPassed(true);

			when(credentialsValidator.check("valid_credentials")).thenReturn(Optional.of(user));
			when(solutionService.getByUser(1L)).thenReturn(List.of(solution));

			ResponseEntity<SolutionListResponse> response = controller.getByUser(request);

			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getBody());
			
			assertEquals(1, response.getBody().listSolutions().size()); 
			
			SolutionResponse mappedSolution = response.getBody().listSolutions().get(0);
			assertEquals(100L, mappedSolution.solutionId());
			assertEquals(10L, mappedSolution.levelId());
			assertEquals(1L, mappedSolution.userId());
			assertEquals("print('Hello World')", mappedSolution.code());
			assertTrue(mappedSolution.isPassed());

			verify(credentialsValidator, times(1)).check("valid_credentials");
			verify(solutionService, times(1)).getByUser(1L);
		}
}
