package com.katkatniss.calculator_service.bean.operation.impl;

import com.katkatniss.calculator_service.bean.operation.Operation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("*")
public class MultiplyOperation extends Operation {

  @Override
  protected BigDecimal calculateWithScaleZero(BigDecimal a, BigDecimal b, int scale) {
    return a.multiply(b).setScale(scale, BigDecimal.ROUND_HALF_UP);
  }

}
