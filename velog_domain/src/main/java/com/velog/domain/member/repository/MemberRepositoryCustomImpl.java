package com.velog.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.member.Member;
import lombok.RequiredArgsConstructor;

import static com.velog.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByEmail(String email) {
        return queryFactory.selectFrom(member)
                .where(
                        member.email.eq(email)
                )
                .fetchOne();
    }

}
