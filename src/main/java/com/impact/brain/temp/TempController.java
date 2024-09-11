package com.impact.brain.temp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class TempController {
    @GetMapping("/check")
    public ResponseEntity<String> healthCheck() {
        String checkStatus = "Healthy";
        return ResponseEntity.ok(checkStatus);
    }
}
