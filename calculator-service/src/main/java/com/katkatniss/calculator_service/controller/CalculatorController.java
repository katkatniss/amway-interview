package com.katkatniss.calculator_service.controller;

import com.katkatniss.calculator_service.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
  @Autowired
  private CalculatorService calculatorService;

  @GetMapping("/{num1}/{num2}")
  public ResponseEntity<String> calculator(@PathVariable String num1, @PathVariable String num2) {
    return calculator(num1 + "/" + num2);
  }

  @GetMapping("/{value}")
  public ResponseEntity<String> calculator(@PathVariable String value) {
    try {
      return ResponseEntity.ok(calculatorService.calculate(value).toPlainString());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (ArithmeticException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/undo")
  public ResponseEntity<BigDecimal> undo() {
    return ResponseEntity.ok(calculatorService.undo());
  }

  @GetMapping("/redo")
  public ResponseEntity<BigDecimal> redo() {
    return ResponseEntity.ok(calculatorService.redo());
  }

}
