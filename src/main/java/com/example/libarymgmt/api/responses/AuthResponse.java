package com.example.libarymgmt.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class AuthResponse {

  private String username;
  private String accessToken;
}
