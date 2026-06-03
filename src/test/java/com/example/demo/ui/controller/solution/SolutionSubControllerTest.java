package com.example.demo.ui.controller.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.core.Authentication;

import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.SolutionService;
import com.example.demo.domain.service.UserService;
import com.example.demo.ui.controller.Solution.SolutionSubController;
import com.example.demo.ui.dtos.solution.SubmitRequest;
import com.example.demo.ui.dtos.solution.SubmitResponse;
import com.example.demo.ui.dtos.solution.*;

@ExtendWith(MockitoExtension.class)
public class SolutionSubControllerTest {

    @Mock
    private SolutionService solutionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SolutionSubController controller;


    @Test
    void shouldReturnUnauthorizedWhenUserInvalid() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        ResponseEntity<SubmitResponse> response =
                controller.submit(new SubmitRequest("code", 1L), auth);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        verify(solutionService, times(0))
                .submit(anyString(), anyLong(), anyLong());
    }

    @Test
    void shouldReturnOkWhenSubmitIsSuccessful() {
        User user = new User();
        user.setId(1L);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(solutionService.submit(anyString(), anyLong(), anyLong()))
                .thenReturn(new SubmitResponse("ok", true));

        ResponseEntity<SubmitResponse> response =
                controller.submit(new SubmitRequest("code", 1L), auth);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().success());

        verify(solutionService, times(1))
                .submit(anyString(), anyLong(), anyLong());
    }
}
