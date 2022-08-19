package com.example.libarymgmt.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {
  private boolean success;
  private String message;
}
