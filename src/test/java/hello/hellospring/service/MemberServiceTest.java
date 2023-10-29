package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class MemberServiceTest {
    // => MemberService 에서 command shift t 하면 테스트 케이스 생성된다.

    // MemberService memberService = new MemberService();
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // MemoryMemberRepository <- 메모리 클리어하기 위함
    // 근데 memorymemberrepository 가 멤버 서비스에 이미 있잖아, 또 new 하기는 좀 그렇지? 다른 객체니까
    // 지금은 static 으로 되어있어서 괜찮겠지, static 아니면? 다른 디비를 참조하는거니까 절대 X

    // public class MemoryMemberRepository implements MemberRepository {
    //   MemoryMemberRepository => 구현체 (레포 인터페이스 정의해놓은거를 여기서 오버라이딩
    //   맥북 기준 - Option + Enter 하면 MemberRepository 에 있는 함수들 가지고 옴
    //   save -> 어딘가에 저장을 해야되겠지? => map 사용

    // 그럼 어떻게 해야하냐?
    // MemberService 에 저 MemberService 선언부를 아래와 같이 바꿔라
    // public MemberService(MemberRepository memberRepository) {
    //     this.memberRepository = memberRepository;
    // }
    //  외부에서 선언하는 방식으로
    //  그럼 어떻게 할 수 있냐?

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given, when, then 테스트 기법
        // given - 데이터 넣기
        Member member = new Member();
        member.setName("spring");
        // spring 으로 넣으면 터져버린다 (회원가입에서 터지겠지) => 메모리 클리어를 해줘야 함

        // when - 언제? - join 할 때 데이터를 넣음
        Long saveId = memberService.join(member);
        // command optoon v => 리턴값으로 반환


        // then - 검증 : 우리가 회원가입한게 리포지토리에 있는게 맞아?
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        // id 값으로 찾은 애랑 저장한 애랑 이름 같은지 비교
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // 이거하고나서 2번째에서 터져야겠지 (같은 이름이니까)

        // try {
        //     memberService.join(member2);
        //     fail("예외가 발생해야합니다.");
        // } catch (IllegalStateException e){
        //     assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다 12");
        //     // string 값이 다르면 터졌다는 말이겠지
        // }
        // 이거 때문에 try catch 쓰기는 좀 애매해

        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 메세지로도 확인 가능
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

        // Assertions.assertThrows(NullPointerException.class, () -> memberService.join(member2));
        // 뒤에 함수 실행했을 때 앞에꺼랑 똑같은게 실행되어야 해
        // => fail 난다 (다른 에러니까)


        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}