package com.jmt.member.repositories;

import com.jmt.member.etities.Authorities;
import com.jmt.member.etities.AuthoritiesId;
import com.jmt.member.etities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>, QuerydslPredicateExecutor<Authorities> {

    List<Authorities> findByMember(Member member);
}