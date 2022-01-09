package com.ndayal.my_hiit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WORKOUT")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "trending")
    private boolean trending;

    @ManyToMany()
    @JoinTable(name = "WORKOUT_USER_REL",
                joinColumns = @JoinColumn(name = "workout_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Workout(String name, List<User> users, String description, boolean trending) {
        this.name = name;
        this.users = users;
        this.description = description;
        this.trending = trending;
    }

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Workout(String name, String description, boolean trending) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
        this.trending = trending;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trending=" + trending +
                '}';
    }
}
