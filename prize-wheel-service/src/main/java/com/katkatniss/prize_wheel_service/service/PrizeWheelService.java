package com.katkatniss.prize_wheel_service.service;

import com.katkatniss.prize_wheel_service.bean.Prize;

import java.util.List;

public interface PrizeWheelService {
  public void setupPrizes(List<Prize> prizes);

  public List<String> draw(int times);
}
