package me.capstone.javis.team.data.repository;

import me.capstone.javis.team.data.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamCustomRepository {

    //이 메서드를 사용하면 실행이 안됌 왜일까?, 내생각엔 UserTeam 까지 연관관계가 걸려있기 때문에 찾지 못하는듯.
    //List<String> findAllUserNameByTeam(Team team);
}
