package com.playdiary.playdiarygpt.controller;

import com.playdiary.playdiarygpt.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/openai")
public class OpenAiController {

    private final OpenAiService openAiService;

    @Autowired
    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePrompt(@RequestBody String prompt) {
        String response = openAiService.callOpenAiApi(prompt);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generatePrompt(@RequestBody String prompt) {
        String response = openAiService.generateText(prompt);

        return ResponseEntity.ok(response);
    }
}
