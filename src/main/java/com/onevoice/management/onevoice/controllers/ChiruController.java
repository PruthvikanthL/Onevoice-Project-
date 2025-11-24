package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.service.ChiruService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/chiru")
public class ChiruController {

    private final ChiruService chiruService;

    public ChiruController(ChiruService chiruService) {
        this.chiruService = chiruService;
    }


    @PostMapping("/ask")
    public ResponseEntity<String> askChiru(@RequestBody String message, Principal principal) {
        String response = chiruService.getChatResponse(message, principal);
        return ResponseEntity.ok(response);
    }
}
