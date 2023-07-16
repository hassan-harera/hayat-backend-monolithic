package com.harera.hayat.service.ai;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.harera.hayat.model.ai.Prediction;

@Service
public class PredictionService {

    public Prediction predict(String text) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://146.190.206.136:5000/predict";

        String requestBody = "{\"text\":\"%s\"}".formatted(text);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Prediction> responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST, requestEntity, Prediction.class);
        return responseEntity.getBody();
    }
}
