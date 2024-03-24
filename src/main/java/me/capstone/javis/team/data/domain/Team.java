package me.capstone.javis.team.data.domain;

import io.micrometer.core.instrument.util.AbstractPartition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.common.domain.BaseTimeEntity;
import me.capstone.javis.teamtodo.data.domain.TeamTodo;
import me.capstone.javis.userteam.domain.UserTeam;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teams")
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TeamTodo> teamTodoList = new ArrayList<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserTeam> userToTeamList = new ArrayList<>();

    @Builder
    public Team(String name)
    {
        this.name = name;
    }
}
