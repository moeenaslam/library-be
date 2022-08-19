package com.example.libarymgmt.member;

import com.example.libarymgmt.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_members")
@Getter
@Setter
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String role;
  private String address;

  @JsonIgnore
  @OneToMany(mappedBy = "loanedToMember")
  private Set<Loan> bookLoans = new HashSet<>();


}
