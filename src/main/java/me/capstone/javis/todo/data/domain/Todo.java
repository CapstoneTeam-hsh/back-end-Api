package me.capstone.javis.todo.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.category.data.domain.Category;
import me.capstone.javis.common.domain.BaseTimeEntity;
import me.capstone.javis.location.data.domain.Location;
import me.capstone.javis.user.data.domain.User;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "todo")
public class Todo extends BaseTimeEntity {

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

    @Column(nullable = false)
    private Boolean checkAlarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;


    //cascade 전략을 사용하여, todo를 삭제하면, 해당 Location도 삭제된다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @Builder
    protected Todo(String title, String contents, String startLine, String deadLine, Category category, Location location){
        this.title =title;
        this.contents = contents;
        this.startLine = startLine;
        this.deadLine = deadLine;
        this.category = category;
        this.location = location;
        this.completed = false;
        this.checkAlarm = false;
    }

    public void updateTitle(String title){this.title = title;}
    public void updateContent(String contents){this.contents = contents;}
    public void updateStartLine(String startLine){this.startLine = startLine;}
    public void updateDeadLine(String deadLine){this.deadLine = deadLine;}

    public void checkCompleted(){this.completed = !completed;}
}
