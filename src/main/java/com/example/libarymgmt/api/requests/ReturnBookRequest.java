package com.example.libarymgmt.api.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReturnBookRequest {
  private Date returnedOn;
}
