package com.katkatniss.prize_wheel_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DrawResponse {

  List<String> prizes;

  int times;

}
