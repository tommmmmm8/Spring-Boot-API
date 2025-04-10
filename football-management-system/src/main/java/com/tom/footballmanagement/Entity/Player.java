package com.tom.footballmanagement.Entity;

import com.tom.footballmanagement.DTO.PlayerResponseDTO;
import com.tom.footballmanagement.Enum.Position;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = true)
    private LocalDate date_of_birth;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "players_team_id_fk"))
    private Team team;

    @Column
    private LocalDate contract_end_date;

    public Player(Long id,
                  String first_name,
                  String last_name,
                  LocalDate date_of_birth,
                  String nationality,
                  Position position,
                  Team team,
                  LocalDate contract_end_date) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.nationality = nationality;
        this.position = position;
        this.team = team;
        this.contract_end_date = contract_end_date;
    }

    public Player() {

    }

    public Player(Player player) {
        this.id = player.getId();
        this.first_name = player.getFirst_name();
        this.last_name = player.getLast_name();
        this.date_of_birth = player.getDate_of_birth();
        this.nationality = player.getNationality();
        this.position = player.getPosition();
        this.team = player.getTeam();
        this.contract_end_date = player.getContract_end_date();
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDate getContract_end_date() {
        return contract_end_date;
    }

    public void setContract_end_date(LocalDate contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", nationality='" + nationality + '\'' +
                ", position='" + position + '\'' +
                ", team='" + team + '\'' +
                ", contract_end_date=" + contract_end_date +
                '}';
    }

    public PlayerResponseDTO toResponseDTO() {
        return new PlayerResponseDTO(
                this.id,
                this.first_name,
                this.last_name,
                this.date_of_birth,
                this.nationality,
                this.position,
                this.team != null ? this.team.getName() : null,
                this.contract_end_date
        );
    }
}

