package com.example.libarymgmt.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

  @Query("SELECT new com.example.libarymgmt.member.MemberDto(mmbr.id,mmbr.name,mmbr.address) FROM Member mmbr " +
      "ORDER BY mmbr.name ASC")
  List<MemberDto> getMembers();
}
