package com.katkatniss.prize_wheel_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katkatniss.prize_wheel_service.bean.Prize;
import com.katkatniss.prize_wheel_service.dto.SetupPrizeRequest;
import com.katkatniss.prize_wheel_service.service.PrizeWheelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PrizeWheelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private PrizeWheelService prizeWheelService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void setupPrizes_Success() throws Exception {
    List<Prize> prizes = List.of(new Prize("A", 10, 1.0));
    SetupPrizeRequest pr = new SetupPrizeRequest(prizes);

    this.mockMvc.perform(post("/prize-wheel/setupPrizes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(pr)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("成功")));

    verify(prizeWheelService, times(1)).setupPrizes(prizes);
  }

  @Test
  public void setupPrizes_ShouldReturnBadRequest_WhenWithoutPrizes() throws Exception {
    SetupPrizeRequest pr = new SetupPrizeRequest(null);

    this.mockMvc.perform(post("/prize-wheel/setupPrizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pr)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("獎品不能為空")));
  }

  @Test
  public void setupPrizes_ShouldReturnBadRequest_WhenThrowException() throws Exception {
    List<Prize> prizes = List.of(new Prize("A", 10, 1.0));
    SetupPrizeRequest pr = new SetupPrizeRequest(prizes);
    doAnswer(invocation -> { throw new Exception(); })
        .when(prizeWheelService)
        .setupPrizes(prizes);

    this.mockMvc.perform(post("/prize-wheel/setupPrizes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pr)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("無法設定獎品")));
    System.out.println(content());
  }

  @Test
  public void draw_ShouldSuccess() throws Exception {
    when(prizeWheelService.draw(1)).thenReturn(List.of("銘謝惠顧"));
    this.mockMvc.perform(get("/prize-wheel/draw/{times}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").value("銘謝惠顧"));
  }

  @Test
  public void draw_ShouldReturnBadRequest_WithIncorrectTimes() throws Exception {
    this.mockMvc.perform(get("/prize-wheel/draw/{times}", "a"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("抽獎次數必須是數字")));
    this.mockMvc.perform(get("/prize-wheel/draw/{times}", 0))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("抽獎次數必須大於0")));
  }

}
