package com.tom.footballmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate founded_year;
    private String stadium;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id", unique = true, foreignKey = @ForeignKey(name = "teams_team_id_fk"))
    @JsonManagedReference // Parent/owner side
    private Coach coach;

    @Column(nullable = false)
    private String league;

    public Team(Long id,
                String name,
                LocalDate founded_year,
                String stadium,
                Coach coach,
                String league) {
        this.id = id;
        this.name = name;
        this.founded_year = founded_year;
        this.stadium = stadium;
        this.coach = coach;
        this.league = league;
    }

    public Team() {
    }

    public Team(Team that) {
        this(that.getId(), that.getName(), that.getFounded_year(), that.getStadium(), that.getCoach(), that.getLeague());
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

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        System.out.println("---- setCoach called ----");
        System.out.println("Current coach: " + (this.coach != null ? this.coach.getId() : "null"));
        System.out.println("New coach: " + (coach != null ? coach.getId() : "null"));
        if (this.coach == coach)
            return;

        if (coach != null) {
            if (coach.getTeam() != null && coach.getTeam() != this) {
                System.out.println("Coach is assigned to a different team");
                return;
            }
        }

        Coach oldCoach = this.coach;

        this.coach = coach;
        System.out.println("New coach assigned to team.");

        if (this.coach != null && this.coach.getTeam() != this) { // If the new coach is not null and the new coach's team is not this team yet
            this.coach.setTeam(this);
            System.out.println("Updated new coach's team reference.");
        }

        if (oldCoach != null) { // The old coach still has this team assigned, we're gonna set it to null
            if (oldCoach.getTeam() == this) {
                System.out.println("oldCoach object id: " + oldCoach.getId());
                oldCoach.setTeam(null);
                System.out.println(oldCoach.getFirst_name() + " " + oldCoach.getLast_name() + " no longer has " + this.getName() + " as a team");
                //System.out.println("Removing old coach " + oldCoach.getFirst_name() + " " + oldCoach.getLast_name() + " from this team.");
            }
        }
        System.out.println("---- setCoach completed ----");
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
