package study.querydsl.repository;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;

import java.util.List;

import static study.querydsl.entity.QMember.member;

public class MemberRepositoryImpl extends QuerydslRepositorySupport {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    public List<MemberTeamDto> search(MemberSearchCondition condition, Pageable pageable) {
        JPQLQuery<MemberTeamDto> jpaQuery = from(member)
                .leftJoin(member.team, QTeam.team)
                .where(member.userName.eq("asdf"))
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.userName,
                        member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                ));

        JPQLQuery<MemberTeamDto> query = getQuerydsl().applyPagination(pageable, jpaQuery);
        return query.fetch();

    }


}
