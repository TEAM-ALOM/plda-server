package com.playdiary.playdiarygpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.playdiary.playdiarygpt.music.Music;
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

    @PostMapping("/musicTest")
    public String musicTest(@RequestBody String prompt) throws JsonProcessingException {

        return openAiService.parseOpenAiResponse(prompt);
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generatePrompt(@RequestBody String prompt) {
        String response = openAiService.generateText(prompt);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/gpt")
    public ResponseEntity<String> gptTest(@RequestBody String prompt) {
        String response = openAiService.generateChat();

        return ResponseEntity.ok(response);
    }
}
