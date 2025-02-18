package com.katkatniss.calculator_service.bean.operation;

import java.math.BigDecimal;

public abstract class Operation {

  public BigDecimal calculate(BigDecimal a, BigDecimal b, int scale) {
    return calculateWithScaleZero(a, b,scale).stripTrailingZeros();
  }

  protected abstract BigDecimal calculateWithScaleZero(BigDecimal a, BigDecimal b, int scale);
}
