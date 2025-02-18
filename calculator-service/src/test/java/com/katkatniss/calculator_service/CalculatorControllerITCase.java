package com.katkatniss.calculator_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CalculatorControllerITCase {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testCalculatorIntegration() {
    ResponseEntity<String> response = restTemplate.getForEntity("/calculator/2+3", String.class);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("5", response.getBody());
    response = restTemplate.getForEntity("/calculator/5/3", String.class);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("1.6666666667", response.getBody());
    response = restTemplate.getForEntity("/calculator/1.333333*1.333333", String.class);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals("1.7777768889", response.getBody());
  }
}
