package com.example.libarymgmt.member;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public List<MemberDto> getMemberList() {
    return memberRepository.getMembers();
  }
}
