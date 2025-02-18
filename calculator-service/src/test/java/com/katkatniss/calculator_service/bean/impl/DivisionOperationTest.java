package com.katkatniss.calculator_service.bean.impl;

import com.katkatniss.calculator_service.bean.operation.impl.DivisionOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DivisionOperationTest {
  @Test
  void testDivision() {
    DivisionOperation division = new DivisionOperation();
    assertEquals(BigDecimal.valueOf(2), division.calculate(BigDecimal.valueOf(6), BigDecimal.valueOf(3), 10));
  }

  @Test
  void testDivisionByZeroThrowsException() {
    DivisionOperation division = new DivisionOperation();
    assertThrows(ArithmeticException.class, () -> division.calculate(BigDecimal.valueOf(6), BigDecimal.ZERO, 10));
  }
}
