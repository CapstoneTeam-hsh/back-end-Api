package me.capstone.javis.userteam.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.common.domain.BaseTimeEntity;
import me.capstone.javis.team.data.domain.Team;
import me.capstone.javis.user.data.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_teams")
public class UserTeam extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public UserTeam(User user, Team team){
        this.user = user;
        this.team = team;
    }
}
