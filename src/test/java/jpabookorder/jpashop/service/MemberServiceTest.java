package jpabookorder.jpashop.service;

import jpabookorder.jpashop.domain.Member;
import jpabookorder.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 실행시 Springboot와 같이 실행
@SpringBootTest // Springboot를 띄운 상태에서 테스트 수행 (없을 경우 Autowired 불가)
@Transactional //테스트 수행 후 테스트 반복을 위해 기본적으로 Rollback 수행
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다
/*
        memberService.join(member1);
        try {
            memberService.join(member2); //예외가 발생해야 한다
        }
        catch (IllegalStateException e) {
            return;
        }
*/
        //then
        fail("예외가 발생해야 한다.");

    }
}