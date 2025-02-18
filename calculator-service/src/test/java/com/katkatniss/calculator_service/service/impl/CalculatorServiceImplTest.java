package com.katkatniss.calculator_service.service.impl;

import com.katkatniss.calculator_service.bean.operation.Operation;
import com.katkatniss.calculator_service.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalculatorServiceImplTest {

  @Autowired
  private CalculatorService calculatorService;

  @Mock
  private Operation plus;

  @Mock
  private Operation minus;

  @Mock
  private Operation multiply;

  @Mock
  private Operation division;

  @Test
  void testPlus() {
    when(plus.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(3), 10)).thenReturn(BigDecimal.valueOf(5));
    BigDecimal result = calculatorService.calculate("2 + 3");
    assertEquals(BigDecimal.valueOf(5), result);
  }

  @Test
  void testMinus() {
    when(minus.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(2), 10)).thenReturn(BigDecimal.valueOf(3));
    BigDecimal result = calculatorService.calculate("5 - 2");
    assertEquals(BigDecimal.valueOf(3), result);
  }

  @Test
  void testMultiply() {
    when(multiply.calculate(BigDecimal.valueOf(4), BigDecimal.valueOf(3), 10)).thenReturn(BigDecimal.valueOf(12));
    BigDecimal result = calculatorService.calculate("4 * 3");
    assertEquals(BigDecimal.valueOf(12), result);
  }

  @Test
  void testDivision() {
    when(division.calculate(BigDecimal.valueOf(10), BigDecimal.valueOf(2), 10)).thenReturn(BigDecimal.valueOf(3.33333333333));
    BigDecimal result = calculatorService.calculate("10 / 3");
    assertEquals(BigDecimal.valueOf(3.3333333333), result);
  }

  @Test
  void testInvalidExpressionThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      calculatorService.calculate("invalid");
    });
    assertEquals("無效運算式", exception.getMessage());
  }

  @Test
  void testDecimalLengthLimit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      calculatorService.calculate("1.123456789012 + 2");
    });
    assertEquals("小數位數不可超過 10 位", exception.getMessage());
  }

  @Test
  void testUndoRedo() {
    // 清空 historyStack 確保測試互不影響
    clearHistory();

    when(plus.calculate(BigDecimal.valueOf(1), BigDecimal.valueOf(3), 10)).thenReturn(BigDecimal.valueOf(4));
    when(minus.calculate(BigDecimal.valueOf(4), BigDecimal.valueOf(1), 10)).thenReturn(BigDecimal.valueOf(3));

    calculatorService.calculate("1 + 3"); // result = 4
    calculatorService.calculate("4 - 1"); // result = 3

    assertEquals(BigDecimal.valueOf(4), calculatorService.undo()); // undo to 4
    assertNull(calculatorService.undo()); // Nothing left to undo

    assertEquals(BigDecimal.valueOf(4), calculatorService.redo()); // Redo back to 4
    assertEquals(BigDecimal.valueOf(3), calculatorService.redo()); // Redo back to 3
    assertNull(calculatorService.redo()); // No more redo steps

    assertEquals(BigDecimal.valueOf(3), calculatorService.undo()); // undo to 3
  }

  private void clearHistory() {
    if (calculatorService.undo() != null) {
      clearHistory();
    }
  }
}
