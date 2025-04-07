package com.tom.footballmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tom.footballmanagement.DTO.TeamResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

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
    @JoinColumn(name = "manager_id", referencedColumnName = "id", unique = true, foreignKey = @ForeignKey(name = "teams_manager_id_fk"))
    @JsonManagedReference // Parent/owner side
    private Manager manager;

    @Column(nullable = false)
    private String league;

    public Team(Long id,
                String name,
                LocalDate founded_year,
                String stadium,
                Manager manager,
                String league) {
        this.id = id;
        this.name = name;
        this.founded_year = founded_year;
        this.stadium = stadium;
        this.manager = manager;
        this.league = league;
    }

    public Team() {
    }

    public Team(Team that) {
        this(that.getId(), that.getName(), that.getFounded_year(), that.getStadium(), that.getManager(), that.getLeague());
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        System.out.println("---- setCoach called ----");
        System.out.println("Current manager: " + (this.manager != null ? this.manager.getId() : "null"));
        System.out.println("New manager: " + (manager != null ? manager.getId() : "null"));
        if (this.manager == manager)
            return;

        if (manager != null) {
            if (manager.getTeam() != null && manager.getTeam() != this) {
                System.out.println("Manager is assigned to a different team");
                return;
            }
        }

        Manager oldManager = this.manager;

        this.manager = manager;
        System.out.println("New manager assigned to team.");

        if (this.manager != null && this.manager.getTeam() != this) { // If the new manager is not null and the new manager's team is not this team yet
            this.manager.setTeam(this);
            System.out.println("Updated new manager's team reference.");
        }

        if (oldManager != null) { // The old manager still has this team assigned, we're gonna set it to null
            if (oldManager.getTeam() == this) {
                System.out.println("oldManager object id: " + oldManager.getId());
                oldManager.setTeam(null);
                System.out.println(oldManager.getFirst_name() + " " + oldManager.getLast_name() + " no longer has " + this.getName() + " as a team");
                //System.out.println("Removing old manager " + oldManager.getFirst_name() + " " + oldManager.getLast_name() + " from this team.");
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
                ", stadium='" + stadium + '\'' +
                ", manager=" + manager +
                ", league='" + league + '\'' +
                '}';
    }

    public TeamResponseDTO toResponseDTO() {
        return new TeamResponseDTO(
                this.id,
                this.name,
                this.founded_year,
                this.stadium,
                this.manager != null ? this.manager.getFirst_name() + " " + this.manager.getLast_name() : null, // if the manager isn't null get the manager and convert it to response DTO otherwise return null
                this.league
        );
    }
}
