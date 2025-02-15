package com.tom.footballmanagement.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate founded_year;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id", unique = true)
    private Coach coach;

    @Column(nullable = false)
    private String league;

    public Team(Long id,
                String name,
                LocalDate founded_year,
                Coach coach,
                String league,
                List<Player> players) {
        this.id = id;
        this.name = name;
        this.founded_year = founded_year;
        this.coach = coach;
        this.league = league;
    }

    public Team() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFounded_year() {
        return founded_year;
    }

    public void setFounded_year(LocalDate foundedYear) {
        this.founded_year = foundedYear;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founded_year=" + founded_year +
                ", coach='" + coach + '\'' +
                ", league='" + league + '\'' +
                '}';
    }
}
