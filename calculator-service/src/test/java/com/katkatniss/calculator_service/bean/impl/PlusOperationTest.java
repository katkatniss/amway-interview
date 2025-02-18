package com.katkatniss.calculator_service.bean.impl;

import com.katkatniss.calculator_service.bean.operation.impl.PlusOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlusOperationTest {

  @Test
  void testAddition() {
    PlusOperation plus = new PlusOperation();
    assertEquals(BigDecimal.valueOf(5), plus.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(3), 10));
    assertEquals(BigDecimal.valueOf(-1), plus.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(-3), 10));
  }

}
