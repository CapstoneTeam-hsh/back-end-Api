package me.capstone.javis.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.capstone.javis.common.exception.customs.CustomException;
import me.capstone.javis.common.exception.customs.ExceptionCode;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.team.data.dto.request.TeamReqDto;
import me.capstone.javis.team.data.dto.response.TeamResDto;
import me.capstone.javis.team.data.dto.response.UserInfoResDto;
import me.capstone.javis.team.data.repository.TeamRepository;
import me.capstone.javis.user.data.domain.User;
import me.capstone.javis.user.data.repository.UserRepository;
import me.capstone.javis.userteam.domain.UserTeam;
import me.capstone.javis.userteam.repository.UserTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;


    public TeamResDto createTeam(String loginId, TeamReqDto teamReqDto)
    {
        User user = userRepository.findByLoginId(loginId).orElseThrow(()->new CustomException(ExceptionCode.USER_NOT_FOUND));
        Team team = TeamReqDto.toEntity(teamReqDto);

        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .build();

        userTeamRepository.save(userTeam);

        return TeamResDto.toDto(teamRepository.save(team));
    }

    public List<UserInfoResDto> addUserToTeam(Long teamId, String loginId)
    {
        User addUser = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException(ExceptionCode.TEAM_NOT_FOUND));

        UserTeam addUserToTeam = UserTeam.builder()
                .user(addUser)
                .team(team)
                .build();

        userTeamRepository.save(addUserToTeam);

        List<UserTeam> userTeamList = team.getUserToTeamList();

        //stream 사용으로 수정
        return userTeamList.stream()
                .map(userTeam -> UserInfoResDto.toDto(userTeam.getUser()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TeamResDto> getAllTeamOfUser(String loginId)
    {
        User reqUser = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));

        return teamRepository.findAllTeamByUser(reqUser);
    }

    @Transactional(readOnly = true)
    public List<String> getUsersOfTeam(Long teamId) {
        Team reqTeam = teamRepository.findById(teamId).orElseThrow(()-> new CustomException(ExceptionCode.TEAM_NOT_FOUND));

        List<String> userNameList = teamRepository.findAllUserByTeam(reqTeam);

        return userNameList;
    }

    public void exitTeam(Long teamId, String loginId){
        Team team = teamRepository.findById(teamId).orElseThrow(()->new CustomException(ExceptionCode.TEAM_NOT_FOUND));
        User user = userRepository.findByLoginId(loginId).orElseThrow(()-> new CustomException(ExceptionCode.USER_NOT_FOUND));
        List<UserTeam> userTeamList = userTeamRepository.findByTeam(team);

        if (userTeamList.size() <= 1){
            teamRepository.deleteById(team.getId());
        }
        else {
            //stream 사용으로 수정, filter 조건에 맞으면 forEach 구문 수행.
            userTeamList.stream()
                    .filter(userTeam -> userTeam.getUser().getId() == user.getId())
                    .forEach(userTeam -> userTeamRepository.deleteById(userTeam.getId()));
        }
    }

}
