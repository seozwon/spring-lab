package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    // 레포지토리는 단순히 저장소에 뭔가 넣었다 뺐다 하는 느낌
    // 기계적으로 개발스럽게? 용어 정의하고 설계함
    // 그냥 데이터 넣었다 뺐다 하는게 원래 롤이니까

    // 레포지토리랑 다르게 서비스는 뭔가 join, findMembers 처럼 비즈니스에 가까운 느낌
    // 비즈니스에 의존적으로 설계를 많이 한다 => 서비스적인 부분(비즈니스)을 정의하는게 원래 롤이니까

    Member save(Member member); //  기능 1 - save (저장)

    Optional<Member> findById(Long id);
    //  기능 2 - find (조회) : id 값으로 회원 찾기 - 없으면 null 반환
    //  => null 그대로 반환 대신 Optional 으로 감싸서 반환하는 방식

    Optional<Member> findByName(String name);
    //  기능 3 - find (조회) : Name 값으로 회원 찾기 - 없으면 null 반환

    List<Member> findAll();

}
