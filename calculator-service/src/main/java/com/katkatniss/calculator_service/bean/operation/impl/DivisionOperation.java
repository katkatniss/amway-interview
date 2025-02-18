package com.katkatniss.calculator_service.bean.operation.impl;

import com.katkatniss.calculator_service.bean.operation.Operation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("/")
public class DivisionOperation extends Operation {

  @Override
  protected BigDecimal calculateWithScaleZero(BigDecimal a, BigDecimal b, int scale) {
    if (b.compareTo(BigDecimal.ZERO) == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }
    return a.divide(b, scale, RoundingMode.HALF_UP);
  }

}
