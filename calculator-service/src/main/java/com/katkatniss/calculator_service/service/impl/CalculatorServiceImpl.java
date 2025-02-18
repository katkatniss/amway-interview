package com.katkatniss.calculator_service.service.impl;

import com.katkatniss.calculator_service.bean.operation.Operation;
import com.katkatniss.calculator_service.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculatorServiceImpl implements CalculatorService {

  @Autowired
  private Map<String, Operation> operations;

  private BigDecimal curr = null;
  private final Stack<BigDecimal> undoStack = new Stack<>();
  private final Stack<BigDecimal> redoStack = new Stack<>();

  private final int scale = 10;
  private final String INVALID_EXPRESSION = "無效運算式";
  private final String DECIMAL_LENGTH_LESS_THEN_TEN = "小數位數不可超過 10 位";

  @Override
  public BigDecimal calculate(String expression) {
    Pattern pattern = Pattern.compile("(-?\\d+(\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(\\.\\d+)?)");
    Matcher matcher = pattern.matcher(expression);

    if (matcher.matches()) {
      BigDecimal num1 = stringToBigDecimal(matcher.group(1));
      BigDecimal num2 = stringToBigDecimal(matcher.group(4));

      String operatorSymbol = matcher.group(3);
      return calculate(num1, num2, operatorSymbol);
    } else {
      throw new IllegalArgumentException(INVALID_EXPRESSION);
    }
  }

  @Override
  public BigDecimal undo() {
    if (curr != null) {
      redoStack.push(curr);
    }

    curr = undoStack.isEmpty() ? null : undoStack.pop();

    return curr;
  }

  @Override
  public BigDecimal redo() {

    if (curr != null) {
      undoStack.push(curr);
    }

    curr = redoStack.isEmpty() ? null : redoStack.pop();

    return curr;
  }

  private BigDecimal stringToBigDecimal(String expression) {
    BigDecimal bd = new BigDecimal(expression);
    validateNumber(bd);
    return bd;
  }

  private void validateNumber(BigDecimal n) {
    if (n.scale() > scale) {
      throw new IllegalArgumentException(DECIMAL_LENGTH_LESS_THEN_TEN);
    }
  }

  private BigDecimal calculate(BigDecimal x, BigDecimal y, String operator) {
    Operation o = operations.get(operator);
    if (o == null) {
      throw new IllegalArgumentException(INVALID_EXPRESSION);
    }

    BigDecimal res = o.calculate(x, y, scale).stripTrailingZeros();

    if (curr != null) {
      undoStack.push(curr);
    }
    curr = res;
    redoStack.clear();
    return res;
  }
}
