package com.lee.querydsl.controller;

import com.lee.querydsl.dto.MemberSearchCondition;
import com.lee.querydsl.dto.MemberTeamDto;
import com.lee.querydsl.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition memberSearchCondition){
        return memberJpaRepository.search(memberSearchCondition);
    }
}
