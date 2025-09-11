package study.data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    // 이름과 나이를 기준으로 회원을 조회하려면
    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //JPA를 직접 사용해서 Named 쿼리 호출
    //@Query(name = "Member.findByUsername") // 쿼리 생략가능
    //List<Member>findByUsername(@Param("username")String username);

    //List<Member> findByUsername(@Param("username") String username);

    //메서드에 JPQL 쿼리 작성
    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int
            age);

    //@Query 값 DTO 조회하기
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 파라미터 바인딩
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name")String username);

    //컬렉션 파라미터 바인딩    Collection 타입으로 in절 지원

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 페이징과 정렬 사용 예제

    Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
    //Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용안함
    //List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사안함
    //List<Member> findByUsername(String name, Sort sort);


}
