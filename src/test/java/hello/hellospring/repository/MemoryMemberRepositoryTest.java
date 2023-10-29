package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
        //   AfterEach => 콜백 함수 : 각 함수(save, findbyid 등) 가 끝날 때마다 실행됨
    }
    @Test
    public void save() {
        Member member = new Member();
        member.setName("seojiwon");

        repository.save(member);
        //  저장하면 id 값 자기가 알아서 세팅하겠지 => /repository 에 MemoryMemberRepository
        //  @Override
        //  public Member save(Member member) { //  스토어에 저장
        //      member.setId(++sequence);
        //      store.put(member.getId(), member);
        //      return member;
        //  }

        Member result = repository.findById(member.getId()).get();
        //  id 값으로 잘 저장되어서 그거 잘 찾는지 확인
        //  이거랑 디비에서 가지고 오는 거랑 정확히 똑같으면
        //  System.out.println("result = " + (result  == member));
        //   "result = true"
        //  저장
        //  근데 매번 프린트 찍을 수는 없잖아 => Assertions 활용
        //  Assertions.assertEquals(member, result);

        assertThat(member).isEqualTo(result);
        //  아무것도 안 떠 => 참
        //  만약 null로 비교하면? 에러남
        //  Expected :hello.hellospring.domain.Member@3c0a50da
        //  Actual   :null
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result.getId()).isEqualTo(member1.getId());
        //  만약 spring2 로 비교하면?
        //  expected: hello.hellospring.domain.Member@6ce139a4
        //  but was: hello.hellospring.domain.Member@1fe20588
        //  Expected :hello.hellospring.domain.Member@6ce139a4
        //  Actual   :hello.hellospring.domain.Member@1fe20588
    }

    // 다 같이 돌리면 터져 => 왜 ? 찾는 멤버 객체가 달라
    //    왜 ? findAll 먼저 해 => member (1, spring1), member (2, spring2) 이야
    //    그리고 findByName 해 => member (3, spring1), member (4, spring2) 이겠지
    //    근데 여기서 findByName 하는 로직이 findAny 야 그럼 가장 빠른거를 찾겠지 ?
    //    그럼 result 에는 id 1 인 애가 저장이 되겠지 (리스트처럼 앞에서부터 찾겠지, index : 0 ?)
    //    근데 member 1 은 id 값이 3인 애야
    //    assertThat(result.getId()) 하면 에러나고, assertThat(result.getName()) 하면 에러 안 나겠지
    //    그럼 레포 새로 만들 때 마다 초기화 해주는 게 필요하겠지 (계속 member id = 1, member id = 2 가 될 수 있게)

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}