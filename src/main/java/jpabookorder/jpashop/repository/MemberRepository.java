package jpabookorder.jpashop.repository;

import jpabookorder.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링빈에 등록
@RequiredArgsConstructor // Spring Data JPA에서 EntityManager를 Autowired 가능하게 해줌
public class MemberRepository {
/*
    @PersistenceContext // EntityManager 주입
    private EntityManager em;
*/

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // from의 대상이 테이블이 아닌 엔티티
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
