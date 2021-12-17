package com.ndayal.my_hiit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "workouts")
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

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "description")
    private String description;

    public Workout(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
