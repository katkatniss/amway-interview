package com.katkatniss.prize_wheel_service.service.impl;

import com.katkatniss.prize_wheel_service.bean.Prize;
import com.katkatniss.prize_wheel_service.service.PrizeWheelService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrizeWheelServiceImpl implements PrizeWheelService {

  private final String NO_PRIZE_MSG = "銘謝惠顧";
  private final String NO_REMAIN_PRIZE_MSG = "獎項已全數抽出";
  private final String TOTAL_PROBABILITY_BIGGER_THAN_ONE = "機率加總不可大於1";

  private List<Prize> prizes = new ArrayList<>();

  private int totalStock = 0;

  private Random random = new Random();

  @Override
  public void setupPrizes(List<Prize> prizes) {
    emptyPrizes();

    double total = 0.0;

    for (Prize prize : prizes) {

      total+= prize.getProbability();
      if (total > 1.0) {
        emptyPrizes();
        throw new BadRequestException(TOTAL_PROBABILITY_BIGGER_THAN_ONE);
      }

      addPrize(prize.getName(), prize.getStock(), prize.getProbability());
    }

  }

  @Override
  public List<String> draw(int times) {
    List<String> results = new ArrayList<>();

    Set<String> drawnPrizes = new HashSet<>(); // 防止重複抽取同樣的獎品

    for (int i = 0; i < times; i++) {

      if (totalStock == 0) {
        results.add(NO_REMAIN_PRIZE_MSG);
        break;
      }

      String prize = drawOnce(drawnPrizes);
      if (prize != null) {
        results.add(prize);
      }

    }
    return results;
  }

  private void addPrize(String name, int stock, double probability) {
    prizes.add(new Prize(name, stock, probability));
    totalStock += stock;
  }

  private void emptyPrizes() {
    prizes = new ArrayList<>();
    totalStock = 0;
  }

  private String drawOnce(Set<String> drawnPrizes) {
    double totalWeight = 1.0;

    double randomValue = random.nextDouble() * totalWeight;
    double weight = 0;

    for (Prize prize : prizes) {
      if (prize.isAvailable() && !drawnPrizes.contains(prize.getName())) {

        weight += prize.getProbability();

        if (randomValue < weight) {
          if (removeOnePrize(prize)) {
            drawnPrizes.add(prize.getName());
            return prize.getName();
          }
        }

      }
    }
    return NO_PRIZE_MSG;
  }

  private boolean removeOnePrize(Prize prize) {
    if (prize.decrementStock()) {
      totalStock--;
      return true;
    }
    return false;
  }

}
