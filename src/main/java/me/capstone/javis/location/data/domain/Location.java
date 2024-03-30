package me.capstone.javis.location.data.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.teamtodo.data.domain.TeamTodo;
import me.capstone.javis.todo.data.domain.Todo;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToOne(mappedBy = "location",cascade = CascadeType.ALL)
    private Todo todo;

    @OneToOne(mappedBy = "location",cascade = CascadeType.ALL)
    private TeamTodo teamTodo;

    @Builder
    protected Location(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateName(String name)
    {
        this.name = name;
    }

    public void updateLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void updateLongitude(double longitude)
    {
        this.longitude = longitude;
    }
}
