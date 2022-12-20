package com.lee.querydsl.repository;

import com.lee.querydsl.dto.MemberSearchCondition;
import com.lee.querydsl.dto.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchComplex(MemberSearchCondition condition, Pageable pageable);

    Page<MemberTeamDto> searchComplexTotalPage(MemberSearchCondition condition, Pageable pageable);
}
