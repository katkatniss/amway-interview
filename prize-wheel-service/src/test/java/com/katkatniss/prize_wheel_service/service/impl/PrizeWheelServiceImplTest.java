package com.katkatniss.prize_wheel_service.service.impl;

import com.katkatniss.prize_wheel_service.bean.Prize;
import com.katkatniss.prize_wheel_service.service.PrizeWheelService;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PrizeWheelServiceImplTest {

  @Autowired
  private PrizeWheelService prizeWheelService;

  @Test
  void setupPrizes_ShouldThrowException_WhenTotalProbabilityExceedsOne() {
    List<Prize> prizes = Arrays.asList(
        new Prize("Prize1", 10, 0.6),
        new Prize("Prize2", 10, 0.5) // Total: 1.1 (Invalid)
    );

    Exception exception = assertThrows(BadRequestException.class, () -> {
      prizeWheelService.setupPrizes(prizes);
    });

    assertEquals("機率加總不可大於1", exception.getMessage());
  }

  @Test
  void setupPrizes_ShouldInitializePrizesCorrectly() {
    List<Prize> prizes = Arrays.asList(
        new Prize("Prize1", 5, 0.3),
        new Prize("Prize2", 10, 0.5)
    );

    prizeWheelService.setupPrizes(prizes);
    List<String> drawResults = prizeWheelService.draw(5);

    assertFalse(drawResults.isEmpty());
  }

  @Test
  void setupPrizes_ShouldReInitializePrizes_WhenSecondCall() {
    List<Prize> prizes = Arrays.asList(
        new Prize("Prize1", 5, 0.3),
        new Prize("Prize2", 10, 0.5)
    );

    prizeWheelService.setupPrizes(prizes);

    prizes = Arrays.asList(
        new Prize("Prize3", 6, 1)
    );

    prizeWheelService.setupPrizes(prizes);

    List<String> drawResults = prizeWheelService.draw(5);

    assertEquals("Prize3", drawResults.get(0));
  }

  @Test
  void draw_ShouldReturnNoRemainPrizeMsg_WhenStockIsZero() {
    List<Prize> prizes = Arrays.asList(
        new Prize("Prize1", 1, 1.0)
    );

    prizeWheelService.setupPrizes(prizes);
    List<String> results = prizeWheelService.draw(2);

    assertEquals(2, results.size());
    assertEquals("獎項已全數抽出", results.get(1));
  }
}
