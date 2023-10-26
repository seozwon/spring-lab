package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
//  기능 1 - save (저장)

    Optional<Member> findById(Long id);
//  기능 2 - find (조회) : id 값으로 회원 찾기 - 없으면 null 반환
//  => null 그대로 반환 대신 Optional 으로 감싸서 반환하는 방식

    Optional<Member> findByName(String name);
//  기능 3 - find (조회) : Name 값으로 회원 찾기 - 없으면 null 반환

    List<Member> findAll();

}
