package com.wvaviator.greenkeep.entities.lawn;

import com.wvaviator.greenkeep.entities.application.Application;
import com.wvaviator.greenkeep.entities.maintenance.Maintenance;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Lawn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String city;
    private String state;

    @Enumerated(EnumType.STRING)
    private USDAHardinessZone hardinessZone;

    @Enumerated(EnumType.STRING)
    private GrassType grassType;

    /**
     * The size of the lawn in square feet.
     */
    private Integer size;

    private Boolean hasSprinklerSystem = false;

    @Column(length = 1000, columnDefinition = "text")
    private String description;

    @Column(length = 1000, columnDefinition = "text")
    private String problems;

    @OneToMany(mappedBy = "lawn", cascade = CascadeType.ALL)
    private List<Maintenance> maintenance;

    @OneToMany(mappedBy = "lawn", cascade = CascadeType.ALL)
    private List<Application> applications;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Lawn() {
    }

    public Lawn(String name, String city, String state, USDAHardinessZone hardinessZone, GrassType grassType, Integer size, Boolean hasSprinklerSystem, String description, String problems) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.hardinessZone = hardinessZone;
        this.grassType = grassType;
        this.size = size;
        this.hasSprinklerSystem = hasSprinklerSystem;
        this.description = description;
        this.problems = problems;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public USDAHardinessZone getHardinessZone() {
        return hardinessZone;
    }

    public void setHardinessZone(USDAHardinessZone hardinessZone) {
        this.hardinessZone = hardinessZone;
    }

    public GrassType getGrassType() {
        return grassType;
    }

    public void setGrassType(GrassType grassType) {
        this.grassType = grassType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getHasSprinklerSystem() {
        return hasSprinklerSystem;
    }

    public void setHasSprinklerSystem(Boolean hasSprinklerSystem) {
        this.hasSprinklerSystem = hasSprinklerSystem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lawn lawn)) return false;

        if (getId() != null ? !getId().equals(lawn.getId()) : lawn.getId() != null) return false;
        if (getName() != null ? !getName().equals(lawn.getName()) : lawn.getName() != null) return false;
        if (getCity() != null ? !getCity().equals(lawn.getCity()) : lawn.getCity() != null) return false;
        if (getState() != null ? !getState().equals(lawn.getState()) : lawn.getState() != null) return false;
        if (getHardinessZone() != lawn.getHardinessZone()) return false;
        if (getGrassType() != lawn.getGrassType()) return false;
        if (getSize() != null ? !getSize().equals(lawn.getSize()) : lawn.getSize() != null) return false;
        if (getHasSprinklerSystem() != null ? !getHasSprinklerSystem().equals(lawn.getHasSprinklerSystem()) : lawn.getHasSprinklerSystem() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(lawn.getDescription()) : lawn.getDescription() != null)
            return false;
        if (getProblems() != null ? !getProblems().equals(lawn.getProblems()) : lawn.getProblems() != null)
            return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(lawn.getCreatedAt()) : lawn.getCreatedAt() != null)
            return false;
        return getUpdatedAt() != null ? getUpdatedAt().equals(lawn.getUpdatedAt()) : lawn.getUpdatedAt() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getHardinessZone() != null ? getHardinessZone().hashCode() : 0);
        result = 31 * result + (getGrassType() != null ? getGrassType().hashCode() : 0);
        result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
        result = 31 * result + (getHasSprinklerSystem() != null ? getHasSprinklerSystem().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getProblems() != null ? getProblems().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        result = 31 * result + (getUpdatedAt() != null ? getUpdatedAt().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lawn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", hardinessZone=" + hardinessZone +
                ", grassType=" + grassType +
                ", size=" + size +
                ", hasSprinklerSystem=" + hasSprinklerSystem +
                ", description='" + description + '\'' +
                ", problems='" + problems + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
