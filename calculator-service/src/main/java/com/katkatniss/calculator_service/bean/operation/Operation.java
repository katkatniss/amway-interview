package com.katkatniss.calculator_service.bean.operation;

import java.math.BigDecimal;

public interface Operation {
  public BigDecimal calculate(BigDecimal a, BigDecimal b, int scale);
}
