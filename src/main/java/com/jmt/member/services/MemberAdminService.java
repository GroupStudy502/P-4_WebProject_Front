package com.jmt.member.services;

import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.global.rests.JSONData;
import com.jmt.member.constants.Authority;
import com.jmt.member.controllers.MemberSearch;
import com.jmt.member.controllers.RequestAuthority;
import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.Member;
import com.jmt.member.entities.QMember;
import com.jmt.member.repositories.AuthoritiesRepository;
import com.jmt.member.repositories.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberAdminService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;

    public Member deleteMember(Long seq) {
        Member member = memberRepository.findById(seq).orElse(null);
        if (member != null) {

            memberRepository.delete(member);
            memberRepository.flush();
            return member;
        }
        return null;
    }

    public JSONData setAuthority(RequestAuthority form) {
        String authorityName = form.getAuthorityName();
        Long memberSeq = form.getMemberSeq();
        boolean invoke = form.isInvoke();

        Member member = memberRepository.findById(memberSeq).orElse(null);
        if (member != null) {
            List<Authorities> authorities = authoritiesRepository.findByMember(member);
            Set<Authority> _items = authorities.stream().map(Authorities::getAuthority).collect(Collectors.toSet());

            //기존 권한 읽어 _items 에 보관 후 _items 에 변경 반영하기
            if(!invoke && _items.contains(Authority.valueOf(authorityName))) { // 권한 삭제
                _items.remove(Authority.valueOf(authorityName));
            }else if(invoke && !_items.contains(Authority.valueOf(authorityName))) { // 권한 부여
                _items.add(Authority.valueOf(authorityName));
            }

            //해당 member의 이전 권한 모두 삭제
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            //_items 으로 부터 새로운 권한 리스트 만들기
            List<Authorities> itemsNew = _items.stream().map(a ->
                            Authorities.builder()
                            .member(member)
                            .authority(a).build()
            ).toList();


            List<Authorities> newAuthorities = authoritiesRepository.saveAllAndFlush(itemsNew);
            System.out.println("newAuthorities : " + newAuthorities);
            JSONData data = new JSONData(newAuthorities);
            data.setMessage("처리되었습니다.");
            data.setSuccess(true);
            return data;
        }
        JSONData data = new JSONData();

        data.setMessage("권한 변경 실패");
        data.setSuccess(false);
        return data;
    }


    public ListData<Member> getList(MemberSearch search) {

        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit > 0 ? limit : 20;

        int offset = (page - 1) * limit;

        String sopt = search.getSopt();
        String skey = search.getSkey();

        QMember member = QMember.member;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /**
         * 조건 검색 처리
         *
         * sopt - ALL : 통합검색(이름 + 이메일 )
         *       NAME : 이름
         *       EMAIL : 이메일
         */
        sopt = sopt != null && StringUtils.hasText(sopt.trim()) ? sopt.trim() : "ALL";
        if (skey != null && StringUtils.hasText(skey.trim())) {
            skey = skey.trim();
            BooleanExpression condition = null;

            BooleanBuilder orBuilder = new BooleanBuilder();

            BooleanBuilder nameCondition = new BooleanBuilder();

            if (sopt.equals("ALL")) { // 통합 검색
                orBuilder.or(member.userName.concat(member.email)
                                .contains(skey))
                        .or(nameCondition);

            } else if (sopt.equals("NAME")) { // 회원명 검색
                condition = member.userName.contains(skey);
            } else if (sopt.equals("EMAIL")) { // 이메일 검색
                condition = member.email.contains(skey);
            }

            if (condition != null) andBuilder.and(condition);
            andBuilder.and(orBuilder);
        }

        String sort = search.getSort();

        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");
        OrderSpecifier orderSpecifier = null;
        Order order = Order.DESC;
        if (sort != null && StringUtils.hasText(sort.trim())) {
            // 정렬항목_방향   예) viewCount_DESC -> 조회수가 많은 순으로 정렬
            String[] _sort = sort.split("_");
            if (_sort[1].toUpperCase().equals("ASC")) {
                order = Order.ASC;
            }

            orderSpecifier = new OrderSpecifier(order, pathBuilder.get(_sort[0]));
        }

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (orderSpecifier != null) {
            orderSpecifiers.add(orderSpecifier);
        }

        List<Member> items = queryFactory
                .selectFrom(member)
                .where(andBuilder)
                //.orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                //.orderBy(orderSpecifier, member.createdAt.desc())
                .orderBy(member.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();


        // 전체 갯수
        long total = memberRepository.count(andBuilder);

        // 페이징 처리
        int ranges = 10;

        Pagination pagination = new Pagination(page, (int)total, ranges, limit, request);

        return new ListData<>(items, pagination);

    }
}
