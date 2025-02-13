package hellojpa;

import jakarta.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory는 로딩시점에 한번만 생성해야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 하나의 트랜잭션을 수행할때마다 EntityManager을 생성해야 한다.
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 회원 등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

            // 회원 조회
//            Member findMember = em.find(Member.class, 1L);

            // JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5) // 페이징 기능(방언 번역)
                .setMaxResults(8)
                .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getName());
            }
            // 회원 수정
            // JPA는 트랜잭션 커밋하는 시점에 변경사항도 체크해서 쿼리를 날림 => 저장 필요X
//            findMember.setName("HelloJPA");

            

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
