package com.katkatniss.calculator_service.bean.impl;

import com.katkatniss.calculator_service.bean.operation.impl.MinusOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MinusOperationTest {
  @Test
  void testSubtraction() {
    MinusOperation minus = new MinusOperation();
    assertEquals(BigDecimal.valueOf(-1), minus.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(3), 10));
    assertEquals(BigDecimal.valueOf(5), minus.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(-3), 10));
  }
}
