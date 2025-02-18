package com.katkatniss.calculator_service.controller;

import com.katkatniss.calculator_service.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CalculatorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CalculatorService calculatorService;

  @Test
  void testCalculator_Success() throws Exception {
    when(calculatorService.calculate("2+3")).thenReturn(BigDecimal.valueOf(5));

    mockMvc.perform(get("/calculator/2+3").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
  }

  @Test
  void testCalculator_InvalidExpression() throws Exception {
    when(calculatorService.calculate("invalid"))
        .thenThrow(new IllegalArgumentException("無效運算式"));

    mockMvc.perform(get("/calculator/invalid").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("無效運算式"));
  }

  @Test
  void testCalculator_DivisionByZero() throws Exception {
    when(calculatorService.calculate("6/0"))
        .thenThrow(new ArithmeticException("除數不能為0"));

    mockMvc.perform(get("/calculator/6/0").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("除數不能為0"));
  }

  @Test
  void testUndo() throws Exception {
    when(calculatorService.undo()).thenReturn(BigDecimal.valueOf(4));

    mockMvc.perform(get("/calculator/undo").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("4"));
  }

  @Test
  void testRedo() throws Exception {
    when(calculatorService.redo()).thenReturn(BigDecimal.valueOf(5));

    mockMvc.perform(get("/calculator/redo").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("5"));
  }
}
