package com.example.demo.ui.controller;

import com.example.demo.domain.entities.Test;
import com.example.demo.ui.dtos.test.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/tests")
public class TestController {

    @PostMapping("/level/")
    public ResponseEntity<List<TestResponse>> getByLevel(@RequestBody long levelId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping("/get")
    public ResponseEntity<TestResponse> getTest(@RequestBody long id) {
        return ResponseEntity.ok(new TestResponse(new Test()));
    }

}
