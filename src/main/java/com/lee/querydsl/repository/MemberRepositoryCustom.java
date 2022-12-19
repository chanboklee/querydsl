package com.lee.querydsl.repository;

import com.lee.querydsl.dto.MemberSearchCondition;
import com.lee.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
