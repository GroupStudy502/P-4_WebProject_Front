package com.jmt.member.repositories;

import com.jmt.member.entities.Authorities;
import com.jmt.member.entities.AuthoritiesId;
import com.jmt.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>, QuerydslPredicateExecutor<Authorities> {

    List<Authorities> findByMember(Member member);

    //@Query("delete from Authorities a where a.member = :k1")
    //Long delAllByMember(@Param("k1") Member k1);

    //@Query("delete from Authorities a where a.member = :k1")
    //Long deleteByMember(@Param("k1") Member k1);
    Long removeByMember(Member member);

}