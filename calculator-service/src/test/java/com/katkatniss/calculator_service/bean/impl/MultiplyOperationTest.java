package com.katkatniss.calculator_service.bean.impl;

import com.katkatniss.calculator_service.bean.operation.impl.MultiplyOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MultiplyOperationTest {

  @Test
  void testMultiplication() {
    MultiplyOperation multiply = new MultiplyOperation();
    assertEquals(BigDecimal.valueOf(6), multiply.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(3), 10));
    assertEquals(BigDecimal.valueOf(-6), multiply.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(-3), 10));
  }
}
