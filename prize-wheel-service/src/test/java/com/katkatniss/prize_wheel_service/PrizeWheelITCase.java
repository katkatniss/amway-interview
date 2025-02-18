package com.katkatniss.prize_wheel_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PrizeWheelITCase {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void setupPrizes_ShouldReturnBadRequest_WhenParameterIsNotListOfPrize() {
    String url = "http://localhost:" + port + "/prize-wheel/setupPrizes";
    List<String> prizes = Arrays.asList("Prize1", "Prize2");

    ResponseEntity<String> response = restTemplate.postForEntity(url, prizes, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void draw_ShouldReturnBadRequest_WithIncorrectTimes() {
    String url = "http://localhost:" + port + "/prize-wheel/draw/a";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(400);
    assertThat(response.getBody()).contains("銘謝惠顧");
  }

  @Test
  public void draw_ShouldReturn_NoPrizeInStock() {
    String url = "http://localhost:" + port + "/prize-wheel/draw/1";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).contains("銘謝惠顧");
  }
}
