package com.example.demo.ui.controller;

import com.example.demo.domain.entities.Test;
import com.example.demo.ui.dtos.test.GetTestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestController {

    @GetMapping("/level/{levelId}")
    public ResponseEntity<List<GetTestResponse>> getByLevel(@PathVariable long levelId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTestResponse> getTest(@PathVariable long id) {
        return ResponseEntity.ok(new GetTestResponse(new Test()));
    }

}
