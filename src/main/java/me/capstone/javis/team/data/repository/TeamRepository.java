package me.capstone.javis.team.data.repository;

import me.capstone.javis.team.data.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamCustomRepository {
}
