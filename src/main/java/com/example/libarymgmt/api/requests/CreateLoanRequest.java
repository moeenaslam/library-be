package com.example.libarymgmt.api.requests;

import com.example.libarymgmt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateLoanRequest {
  private LocalDate loanedAt;
  @NotNull
  private LocalDate dueDate;
  private BigDecimal fee;

  @NotNull
  private Member loanedToMember;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
