package com.tom.footballmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tom.footballmanagement.DTO.ManagerResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "managers")
public class Manager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    private LocalDate date_of_birth;
    private String nationality;

    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", unique = true)
    @JsonBackReference // Child/inverse side
    private Team team;

    public Manager(Long id,
                   String first_name,
                   String last_name,
                   LocalDate date_of_birth,
                   String nationality,
                   Team team) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.nationality = nationality;
        this.team = team;
    }

    public Manager() {}

    public Manager(Manager that) {
        this(that.getId(), that.getFirst_name(), that.getFirst_name(), that.getDate_of_birth(), that.getNationality(), that.getTeam());
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        System.out.println("---- setTeam called ----");
        System.out.println("Current team: " + (this.team != null ? this.team.getId() : "null"));
        System.out.println("New team: " + (team != null ? team.getId() : "null"));
        if (this.team == team)
            return;

        if (team != null) {
            if (team.getManager() != null && team.getManager() != this) {
                System.out.println("The team already has a coach");
                return;
            }
        }

        Team oldTeam = this.team;

        this.team = team;
        System.out.println("New team assigned to coach.");

        if (this.team != null && this.team.getManager() != this) { // If the new team is not null and the new team's coach is not this coach yet
            this.team.setManager(this);
            System.out.println("Updated new team's coach reference.");
        }

        if (oldTeam != null) { // If the old team still has this coach assigned, we're gonna set it to null
            if (oldTeam.getManager() == this) {
                System.out.println("oldTeam object id: " + oldTeam.getId());
                oldTeam.setManager(null);
                System.out.println(oldTeam.getName() + " no longer has " + this.getFirst_name() + " " + this.getLast_name() + " as a coach");
                //System.out.println("Removing old coach " + this.getFirst_name() + " " + this.getLast_name() + " from this team.");
            }
        }
        System.out.println("---- setTeam completed ----");
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", nationality='" + nationality + '\'' +
                ", team=" + team +
                '}';
    }

    public ManagerResponseDTO toResponseDTO() {
        return new ManagerResponseDTO(
                this.id,
                this.first_name,
                this.last_name,
                this.date_of_birth,
                this.nationality,
                this.team != null ? this.team.getName() : null);
    }
}
