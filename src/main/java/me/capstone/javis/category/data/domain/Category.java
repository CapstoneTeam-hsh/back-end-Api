package me.capstone.javis.category.data.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.capstone.javis.common.domain.BaseTimeEntity;
import me.capstone.javis.todo.data.domain.Todo;
import me.capstone.javis.user.data.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @Builder
    public Category(String name,User user){
        this.name=  name;
        this.user = user;
    }

    public void updateName(String name){
        this.name = name;
    }

}
