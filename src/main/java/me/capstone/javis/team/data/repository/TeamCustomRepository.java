package me.capstone.javis.team.data.repository;

import me.capstone.javis.team.data.dto.response.TeamResDto;
import me.capstone.javis.user.data.domain.User;

import java.util.List;

public interface TeamCustomRepository {
    List<TeamResDto> findAllTeamByUser(User reqUser);
}
