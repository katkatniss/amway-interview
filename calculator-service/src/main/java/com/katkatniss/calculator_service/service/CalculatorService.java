package com.katkatniss.calculator_service.service;

import java.math.BigDecimal;

public interface CalculatorService {
  public BigDecimal calculate(String cmd);

  public BigDecimal undo();

  public BigDecimal redo();
}
