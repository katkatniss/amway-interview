package com.katkatniss.prize_wheel_service.controller;

import com.katkatniss.prize_wheel_service.bean.Prize;
import com.katkatniss.prize_wheel_service.dto.SetupPrizeRequest;
import com.katkatniss.prize_wheel_service.service.PrizeWheelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prize-wheel")
public class PrizeWheelController {

  @Autowired
  private PrizeWheelService prizeWheelService;

  private final String PRIZES_SUCCESS = "成功";
  private final String PRIZES_CANNOT_BE_EMPTY = "獎品不能為空";
  private final String CANNOT_SETUP_PRIZES = "無法設定獎品";
  private final String SHOULD_BE_A_NUMBER = "抽獎次數必須是數字";
  private final String SHOULD_BIGGER_THAN_ZERO = "抽獎次數必須大於0";

  @PostMapping("/setupPrizes")
  public ResponseEntity<String> setupPrizes(@RequestBody SetupPrizeRequest spr) {
    if (spr == null || spr.getPrizes() == null || spr.getPrizes().isEmpty()) {
      return ResponseEntity.badRequest().body(PRIZES_CANNOT_BE_EMPTY);
    }

    List<Prize> prizes = spr.getPrizes();

    try {
      prizeWheelService.setupPrizes(prizes);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(CANNOT_SETUP_PRIZES + " " + e.getMessage());
    }

    return ResponseEntity.ok(PRIZES_SUCCESS);
  }

  @GetMapping("/draw/{times}")
  public ResponseEntity<?> draw(@PathVariable String times) {

    if (!times.matches("\\d+")) {
      return ResponseEntity.badRequest().body(SHOULD_BE_A_NUMBER);
    }

    int drawTimes = Integer.parseInt(times);
    if (drawTimes < 1) {
      return ResponseEntity.badRequest().body(SHOULD_BIGGER_THAN_ZERO);
    }

    return ResponseEntity.ok(prizeWheelService.draw(drawTimes));
  }

}
