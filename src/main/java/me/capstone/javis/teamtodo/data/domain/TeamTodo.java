package me.capstone.javis.teamtodo.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.common.domain.BaseTimeEntity;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.team.data.domain.Team;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team_todos")
public class TeamTodo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private String startLine;

    @Column(nullable = false)
    private String deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @Builder
    public TeamTodo(String title, String contents, String startLine, String deadLine,Team team, Location location){
        this.title = title;
        this.contents =contents;
        this.startLine =startLine;
        this.deadLine = deadLine;
        this.team =team;
        this.location =location;
        this.completed = false;
    }

    public void updateTitle(String title){this.title = title;}
    public void updateContent(String contents){this.contents = contents;}
    public void updateStartLine(String startLine){this.startLine = startLine;}
    public void updateDeadLine(String deadLine){this.deadLine = deadLine;}
    public void checkTeamCompleted(){this.completed = !completed;}
}
