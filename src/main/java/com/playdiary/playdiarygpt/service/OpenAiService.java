package com.playdiary.playdiarygpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playdiary.playdiarygpt.music.Music;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/completions";
    private String output ="<200 OK OK,{warning=This model version is deprecated. Migrate before January 4, 2024 to avoid disruption of service. Learn more https://platform.openai.com/docs/deprecations, id=cmpl-7rpP0jGfJw4EXPhgndSozzPKXyiNs, object=text_completion, created=1693063330, model=text-davinci-003, choices=[{text=\n" +
            "\"BOM - BTS, 한 바퀴 돌아 - 레드벨벳, 울면서 바라봐 - 브라운 아이드 소울, index=0, logprobs=null, finish_reason=stop}], usage={prompt_tokens=394, completion_tokens=79, total_tokens=473}},[Date:\"Sat, 26 Aug 2023 15:22:11 GMT\", Content-Type:\"application/json\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\", access-control-allow-origin:\"*\", Cache-Control:\"no-cache, must-revalidate\", openai-model:\"text-davinci-003\", openai-organization:\"user-lvlegung1zgxpad9lmurfouy\", openai-processing-ms:\"1299\", openai-version:\"2020-10-01\", strict-transport-security:\"max-age=15724800; includeSubDomains\", x-ratelimit-limit-requests:\"3000\", x-ratelimit-limit-tokens:\"250000\", x-ratelimit-limit-tokens_usage_based:\"250000\", x-ratelimit-remaining-requests:\"2999\", x-ratelimit-remaining-tokens:\"249900\", x-ratelimit-remaining-tokens_usage_based:\"249900\", x-ratelimit-reset-requests:\"20ms\", x-ratelimit-reset-tokens:\"24ms\", x-ratelimit-reset-tokens_usage_based:\"24ms\", x-request-id:\"fd041911f952d7b0328ef1f66303d06d\", CF-Cache-Status:\"DYNAMIC\", Server:\"cloudflare\", CF-RAY:\"7fcd11954b3cc185-ICN\", alt-svc:\"h3=\":443\"; ma=86400\"]>";

    public String parseTest(){
        String s1 = "text=";
        String s2 = "=stop";

        return "sorry";
    }
    public String generateText(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","text-davinci-003");
        //requestBody.put("model","gpt-3.5-turbo");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", 1.0f);
        requestBody.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
        String fix = response.toString();
        fix = fix.replaceFirst("<200 OK OK,", "");
        return fix;
    }

    public String generateChat() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","gpt-3.5-turbo");
        requestBody.put("message", "[{\"role\": \"system\", \"content\" : \"You are a helpful assistant.\"}]");


        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
        String fix = response.toString();
        fix = fix.replaceFirst("<200 OK OK,", "");
        return fix;
    }


    public String parseOpenAiResponse(String jsonData) throws JsonProcessingException {


        Pattern pattern = Pattern.compile("text=([^\"]*finish_reason=stop)");
        Matcher matcher = pattern.matcher(jsonData);

        if (matcher.find()) {
            String extractedText = matcher.group(1);
            System.out.println(extractedText);
            return extractedText;
        }
        else {
            return "ERROR";
        }
    }

}
