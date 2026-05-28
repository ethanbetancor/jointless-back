package com.example.demo.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.data.LevelRepository;
import com.example.demo.data.SolutionRepository;
import com.example.demo.data.TestRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.result.DockerResult;
import com.example.demo.ui.dtos.solution.SubmitResponse;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceTest {
	@Mock
	private SolutionRepository solutionRepository;
	
	@Mock
	private DockerService dockerService;
	
	@InjectMocks
	private SolutionService solutionService;
	
	@Mock
	private TestRepository testRepository;
	
	@Mock
    private LevelRepository levelRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	void shouldReturnSuccessExitWhenPassedAGoodCode() {
		String code="public class Solution {\\n public int sum(int a, int b) {\\n return a - b\\n }\\n}";
		Long levelId = 1L;
		Long userId=1L;
		
		 com.example.demo.domain.entities.Test testEntity =
	                new com.example.demo.domain.entities.Test();

	     testEntity.setId(1L);

	     when(testRepository.findByLevelId(levelId))
	                .thenReturn(testEntity);
	        
	     when(levelRepository.findById(levelId))
	         .thenReturn(Optional.of(new Level()));
	        
	     when(userRepository.findById(userId))
	        .thenReturn(Optional.of(new User()));
	        
	     when(dockerService.runInContainer(anyString(), anyLong()))
            .thenReturn(DockerResult.create("Succeded", 0));
	        
		 
		 
		 SubmitResponse response = solutionService.submit(code, levelId, userId);
		 
		 assertTrue(response.success());
	        verify(solutionRepository, times(1))
	                .save(any(Solution.class));
	}
	
	@Test
	void shouldReturnFailWhenDockerFails() {

	    Long levelId = 1L;
	    Long userId = 1L;

	    com.example.demo.domain.entities.Test testEntity =
	            new com.example.demo.domain.entities.Test();
	    testEntity.setId(1L);

	    when(testRepository.findByLevelId(levelId))
	            .thenReturn(testEntity);

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.error("Compilation error"));

	    SubmitResponse response =
	            solutionService.submit("code", levelId, userId);

	    assertFalse(response.success());

	    verify(solutionRepository, times(0))
	            .save(any());
	}
	
	
	@Test
	void shouldThrowWhenUserDoesNotExist() {

		 when(levelRepository.findById(1L))
         .thenReturn(Optional.of(new Level()));

 
		 com.example.demo.domain.entities.Test testEntity =
		         new com.example.demo.domain.entities.Test();
		 testEntity.setId(1L);
		
		 when(testRepository.findByLevelId(1L))
		         .thenReturn(testEntity);
		
		 when(dockerService.runInContainer(anyString(), anyLong()))
         .thenReturn(DockerResult.create("OK", 0));
		 
		 when(userRepository.findById(1L))
		         .thenReturn(Optional.empty());
		
		 assertThrows(NoSuchElementException.class,
		         () -> solutionService.submit("code", 1L, 1L));
		
		 verify(solutionRepository, times(0))
		         .save(any());
	}
	
	@Test
	void shouldThrowWhenLevelDoesNotExist() {

	    Long levelId = 1L;
	    Long userId = 1L;

	    com.example.demo.domain.entities.Test testEntity =
	            new com.example.demo.domain.entities.Test();
	    testEntity.setId(10L);

	    when(testRepository.findByLevelId(levelId))
	            .thenReturn(testEntity);

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.create("OK", 0));

	    assertThrows(NoSuchElementException.class,
	            () -> solutionService.submit("code", levelId, userId));

	    verify(solutionRepository, times(0))
	            .save(any());
	}
	
	@Test
	void shouldCallAllDependencies() {

	    when(userRepository.findById(anyLong()))
	            .thenReturn(Optional.of(new User()));

	    when(levelRepository.findById(anyLong()))
	            .thenReturn(Optional.of(new Level()));

	    when(testRepository.findByLevelId(anyLong()))
	            .thenReturn(new com.example.demo.domain.entities.Test());

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.create("OK", 0));

	    solutionService.submit("code", 1L, 1L);

	    verify(userRepository).findById(anyLong());
	    verify(levelRepository).findById(anyLong());
	    verify(testRepository).findByLevelId(anyLong());
	    verify(dockerService).runInContainer(anyString(), anyLong());
	    verify(solutionRepository).save(any(Solution.class));
	}
	
	@Test
	void shouldReturnCompileError() {

	    Long levelId = 1L;
	    Long userId = 1L;

	    com.example.demo.domain.entities.Test testEntity =
	            new com.example.demo.domain.entities.Test();
	    testEntity.setId(10L);

	    when(testRepository.findByLevelId(levelId))
	            .thenReturn(testEntity);

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.create("line1\nerror: missing semicolon\nline3", 1));

	    SubmitResponse response =
	            solutionService.submit("code", levelId, userId);

	    assertFalse(response.success());
	    assertTrue(response.message().contains("error:"));
	}
	
	@Test
	void shouldReturnTestFailure() {

	    Long levelId = 1L;
	    Long userId = 1L;

	    com.example.demo.domain.entities.Test testEntity =
	            new com.example.demo.domain.entities.Test();
	    testEntity.setId(10L);

	    when(testRepository.findByLevelId(levelId))
	            .thenReturn(testEntity);

	    String dockerOutput =
	            "some log\nAssertionFailedError\nexpected: 5 but was: 3\nend";

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.create(dockerOutput, 1));

	    SubmitResponse response =
	            solutionService.submit("code", levelId, userId);

	    assertFalse(response.success());
	    assertTrue(response.message().contains("expected:"));
	}
	
	@Test
	void shouldReturnGenericFailureOutput() {

	    Long levelId = 1L;
	    Long userId = 1L;

	    com.example.demo.domain.entities.Test testEntity =
	            new com.example.demo.domain.entities.Test();
	    testEntity.setId(10L);

	    when(testRepository.findByLevelId(levelId))
	            .thenReturn(testEntity);

	    when(dockerService.runInContainer(anyString(), anyLong()))
	            .thenReturn(DockerResult.create("random failure stacktrace", 1));

	    SubmitResponse response =
	            solutionService.submit("code", levelId, userId);

	    assertFalse(response.success());
	    assertEquals("random failure stacktrace", response.message());
	}
}