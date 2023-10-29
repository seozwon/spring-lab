package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // 회원 서비스 : 회원 리포지토리랑 도메인을 활용해서 실제 비즈니스 로직 작성

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    // 외부에서 선언하는 방식으로 바꿔주기 위해 위와 같이 변경

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 외부에서 메모리 멤버 리포지토리를 외부에서 넣어준다 => 디펜던시 인젝션(di, dependency injection)


    public Long join(Member member) { // 회원가입
        // 요구사항 : 같은 이름이 있는 중복 회원 X

        // memberRepository.findByName(member.getName())
        // 커맨드 옵션 V : return 값으로 바꿔줌
        Optional<Member> result = memberRepository.findByName(member.getName());

        // result.ifPresent(m -> {
        //     throw new IllegalStateException("이미 존재하는 회원입니다");
        // };

        // 원래는 if 걸고 해야했는데 : Member member1 = result.get() 이렇게 또는 result.orElseGet
        // 요즘은 Optional 로 반환해주기 때문에 ifPresent 같은 메서드 사용 가능
        // Optional 안에 멤버 객체가 있음 -> Optional 을 통해 여러 메서드 사용 가능
        //
        // 근데 Optional ~~ 하고 result에 ifPresent 하고 이거 별로 안 이쁘자나 => 한번에 가능

        //  .ifPresent(m -> {
        //      throw new IllegalStateException("이미 존재하는 회원입니다");
        //  });
        // => 어떤 로직이 들어간다 : 함수로 뽑을 수 있음 (Ctrl T) => validateDuplicateMember 생성

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    public List<Member> findMembers() {
        // 전체 회원 조회 - 반환값이 List<Member> 인 이유는?
        // 당연히 레포에 정의해둔 findAll의 리턴값이 List<Member> 니까...
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        // 직원 하나 조회
        return memberRepository.findById(memberId);
    }
}
