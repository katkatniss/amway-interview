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

  @PostMapping("/setupPrizes")
  public ResponseEntity<String> setupPrizes(@RequestBody SetupPrizeRequest spr) {
    if (spr == null || spr.getPrizes() == null || spr.getPrizes().isEmpty()) {
      return ResponseEntity.badRequest().body("Prizes cannot be null or empty.");
    }

    List<Prize> prizes = spr.getPrizes();

    try {
      prizeWheelService.setupPrizes(prizes);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Failed to setup prizes. " + e.getMessage());
    }

    return ResponseEntity.ok("Success.");
  }

  @GetMapping("/draw/{times}")
  public ResponseEntity<?> draw(@PathVariable String times) {

    if (!times.matches("\\d+")) {
      return ResponseEntity.badRequest().body("抽獎次數必須是數字");
    }

    int drawTimes = Integer.parseInt(times);
    if (drawTimes < 1) {
      return ResponseEntity.badRequest().body("抽獎次數必須大於0");
    }

    return ResponseEntity.ok(prizeWheelService.draw(drawTimes));
  }

}
