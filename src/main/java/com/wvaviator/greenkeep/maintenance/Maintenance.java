package com.wvaviator.greenkeep.maintenance;

import com.wvaviator.greenkeep.equipment.Equipment;
import com.wvaviator.greenkeep.lawn.Lawn;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MaintenanceType type;

    private Boolean scheduled = false;
    private LocalDateTime scheduledAt;

    private Boolean completed = false;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "lawn_id")
    private Lawn lawn;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Maintenance() {
    }

    public Maintenance(MaintenanceType type, Boolean scheduled, LocalDateTime scheduledAt, Boolean completed, LocalDateTime completedAt) {
        this.type = type;
        this.scheduled = scheduled;
        this.scheduledAt = scheduledAt;
        this.completed = completed;
        this.completedAt = completedAt;
    }

    public Maintenance(MaintenanceType type, Boolean scheduled, LocalDateTime scheduledAt, Boolean completed, LocalDateTime completedAt, Equipment equipment, Lawn lawn) {
        this.type = type;
        this.scheduled = scheduled;
        this.scheduledAt = scheduledAt;
        this.completed = completed;
        this.completedAt = completedAt;
        this.equipment = equipment;
        this.lawn = lawn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaintenanceType getType() {
        return type;
    }

    public void setType(MaintenanceType type) {
        this.type = type;
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Lawn getLawn() {
        return lawn;
    }

    public void setLawn(Lawn lawn) {
        this.lawn = lawn;
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
        if (!(o instanceof Maintenance that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getType() != that.getType()) return false;
        if (getScheduled() != null ? !getScheduled().equals(that.getScheduled()) : that.getScheduled() != null)
            return false;
        if (getScheduledAt() != null ? !getScheduledAt().equals(that.getScheduledAt()) : that.getScheduledAt() != null)
            return false;
        if (getCompleted() != null ? !getCompleted().equals(that.getCompleted()) : that.getCompleted() != null)
            return false;
        if (getCompletedAt() != null ? !getCompletedAt().equals(that.getCompletedAt()) : that.getCompletedAt() != null)
            return false;
        if (getEquipment() != null ? !getEquipment().equals(that.getEquipment()) : that.getEquipment() != null)
            return false;
        if (getLawn() != null ? !getLawn().equals(that.getLawn()) : that.getLawn() != null) return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() != null)
            return false;
        return getUpdatedAt() != null ? getUpdatedAt().equals(that.getUpdatedAt()) : that.getUpdatedAt() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getScheduled() != null ? getScheduled().hashCode() : 0);
        result = 31 * result + (getScheduledAt() != null ? getScheduledAt().hashCode() : 0);
        result = 31 * result + (getCompleted() != null ? getCompleted().hashCode() : 0);
        result = 31 * result + (getCompletedAt() != null ? getCompletedAt().hashCode() : 0);
        result = 31 * result + (getEquipment() != null ? getEquipment().hashCode() : 0);
        result = 31 * result + (getLawn() != null ? getLawn().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        result = 31 * result + (getUpdatedAt() != null ? getUpdatedAt().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                ", type=" + type +
                ", scheduled=" + scheduled +
                ", scheduledAt=" + scheduledAt +
                ", completed=" + completed +
                ", completedAt=" + completedAt +
                ", equipment=" + equipment +
                ", lawn=" + lawn +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
