package com.example.libarymgmt.api.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

  private String username;
  private String password;
}
