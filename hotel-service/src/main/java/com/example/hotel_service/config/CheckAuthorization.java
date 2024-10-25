package com.example.hotel_service.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

public class CheckAuthorization {

    public boolean checkAuthorization(String token, String action) {
        RestTemplate restTemplate = new RestTemplate();
        String authUrl = "http://auth-service/auth/check?action=" + action;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> response = restTemplate.exchange(authUrl, HttpMethod.GET, entity, Boolean.class);

        return response.getBody();
    }
}