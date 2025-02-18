package com.katkatniss.prize_wheel_service.bean;

import lombok.Data;

@Data
public class Prize {
  String name;
  int stock;
  double probability;

  public Prize(String name, int stock, double probability) {
    this.name = name;
    this.stock = stock;
    this.probability = probability;
  }

  public boolean isAvailable() {
    return this.stock > 0;
  }

  public boolean decrementStock() {
    if (this.stock > 0) {
      this.stock--;
      return true;
    }
    return false;
  }
}
