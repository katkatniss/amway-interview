package com.katkatniss.prize_wheel_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.katkatniss.prize_wheel_service.bean.Prize;
import lombok.Data;

import java.util.List;

@Data
public class SetupPrizeRequest {

  @JsonProperty("prizes")
  private List<Prize> prizes;

  public SetupPrizeRequest(List<Prize> prizes) {
    this.prizes = prizes;
  }
}
