package com.playdiary.playdiarygpt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// ChatGPT에 요청할 DTO Format
public class ChatGptRequest {
    private String model;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    private Boolean stream;


    @Builder
    public ChatGptRequest(String model, Integer maxTokens, Double temperature){
        
    }
}
