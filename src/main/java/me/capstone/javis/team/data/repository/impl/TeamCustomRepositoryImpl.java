package me.capstone.javis.team.data.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.team.data.dto.response.TeamResDto;
import me.capstone.javis.team.data.repository.TeamCustomRepository;
import me.capstone.javis.user.data.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static me.capstone.javis.category.data.domain.QCategory.category;
import static me.capstone.javis.team.data.domain.QTeam.team;
import static me.capstone.javis.user.data.domain.QUser.user;
import static me.capstone.javis.userteam.domain.QUserTeam.userTeam;

@Repository
@RequiredArgsConstructor
public class TeamCustomRepositoryImpl implements TeamCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<TeamResDto> findAllTeamByUser(User reqUser) {
        List<Tuple> allTeamOfUser = jpaQueryFactory
                .select(team.id, team.name)
                .from(user)
                .join(userTeam).on(user.id.eq(userTeam.user.id))
                .join(team).on(userTeam.team.id.eq(team.id))
                .where(user.loginId.eq(reqUser.getLoginId()))
                .fetch();

        List<TeamResDto> allTeamOfUserList = allTeamOfUser.stream()
                .map(teamTuple ->{
                    Long teamId = teamTuple.get(team.id);
                    String teamName = teamTuple.get(team.name);
                    return TeamResDto.builder()
                            .id(teamId)
                            .name(teamName)
                            .build();
                }).toList();

        return allTeamOfUserList;
    }

    @Override
    public List<String> findAllUserByTeam(Team reqTeam) {
        List<String> allUserOfTeam = jpaQueryFactory
                .select(user.name)
                .from(team)
                .join(userTeam).on(team.id.eq(userTeam.team.id))
                .join(user).on(userTeam.user.id.eq(user.id))
                .where(team.id.eq(reqTeam.getId()))
                .fetch();

        return allUserOfTeam;
    }


}
