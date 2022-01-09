package com.ndayal.my_hiit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WORKOUT_HISTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_time")
    Date createdTime;

    public WorkoutHistory(User user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "WorkoutHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
