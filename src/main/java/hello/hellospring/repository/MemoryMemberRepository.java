package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
//   MemoryMemberRepository => 구현체

//    맥북 기준 - Option + Enter 하면 MemberRepository 에 있는 함수들 가지고 옴

//    save -> 어딘가에 저장을 해야되겠지? => map 사용

    private static Map<Long, Member> store = new HashMap<>();
//    Ctrl + Space 하면 import 해옴
//    키는 회원 ID(Long), 값은 멤버
    private static long sequence = 0L;
//    sequence(시퀀스) : 0,1,2 이렇게 키값을 생성해주는 애

    @Override
    public Member save(Member member) {
//  스토어에 저장
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//  스토어에서 빼오기
//  return store.get(id);
//  근데 결과가 없으면? null 이겠지 / 요즘에는 null이 반환될 가능성이 있으면 optional 로 감싸준다
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
//              람다식
                .filter(member -> member.getName().equals(name))
//               => 멤버에서 파라미터로 넘어온 네임이랑 같은지 확인, 찾으면 반환
                .findAny();
//              => 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
//        스토어에 있는 values => 멤버들
    }
}

// 구현 다 했어, 동작하는지 확인은 어떻게 하지? => 테스트케이스(TC) 생성!!
// => test 디렉토리
