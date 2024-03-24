package me.capstone.javis.userteam.repository;

import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.userteam.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, Long>{
    List<UserTeam> findByTeam(Team team);
}
