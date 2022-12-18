package com.lee.querydsl.repository;

import com.lee.querydsl.dto.MemberSearchCondition;
import com.lee.querydsl.dto.MemberTeamDto;
import com.lee.querydsl.dto.QMemberTeamDto;
import com.lee.querydsl.entity.Member;
import com.lee.querydsl.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.lee.querydsl.entity.QMember.*;
import static com.lee.querydsl.entity.QTeam.team;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    // 동시성 문제가 발생하지 않는다..
    private final JPAQueryFactory queryFactory;

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl(){
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition memberSearchCondition){

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(memberSearchCondition.getUsername())){
            builder.and(member.username.eq(memberSearchCondition.getUsername()));
        }
        if(StringUtils.hasText(memberSearchCondition.getTeamName())){
            builder.and(team.name.eq(memberSearchCondition.getTeamName()));
        }
        if(memberSearchCondition.getAgeGoe() != null){
            builder.and(member.age.goe(memberSearchCondition.getAgeGoe()));
        }
        if(memberSearchCondition.getAgeLoe() != null){
            builder.and(member.age.loe(memberSearchCondition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();
    }
}
