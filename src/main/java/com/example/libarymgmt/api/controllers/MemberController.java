package com.example.libarymgmt.api.controllers;

import com.example.libarymgmt.loan.LoanDto;
import com.example.libarymgmt.member.MemberDto;
import com.example.libarymgmt.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "members")
public class MemberController extends BaseController{
  static final Logger LOGGER = LogManager.getLogger(MemberController.class);

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "")
  public ResponseEntity<List<MemberDto>> getMemberHistory(){
    try{
      return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
