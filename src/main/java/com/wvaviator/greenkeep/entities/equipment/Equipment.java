package com.wvaviator.greenkeep.entities.equipment;

import com.wvaviator.greenkeep.entities.Maintenance.Maintenance;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    private LocalDateTime purchasedAt = LocalDateTime.now();

    private String brand = "None";

    private String model = "None";

    private String serialNumber = "0000";

    private String image = "https://via.placeholder.com/150";

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<Maintenance> maintenance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Equipment() {
    }

    public Equipment(Long id, EquipmentType type, LocalDateTime purchasedAt, String brand, String model, String serialNumber, String image) {
        this.id = id;
        this.type = type;
        this.purchasedAt = purchasedAt;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment equipment)) return false;

        if (getId() != null ? !getId().equals(equipment.getId()) : equipment.getId() != null) return false;
        if (getType() != equipment.getType()) return false;
        if (getPurchasedAt() != null ? !getPurchasedAt().equals(equipment.getPurchasedAt()) : equipment.getPurchasedAt() != null)
            return false;
        if (getBrand() != null ? !getBrand().equals(equipment.getBrand()) : equipment.getBrand() != null) return false;
        if (getModel() != null ? !getModel().equals(equipment.getModel()) : equipment.getModel() != null) return false;
        if (getSerialNumber() != null ? !getSerialNumber().equals(equipment.getSerialNumber()) : equipment.getSerialNumber() != null)
            return false;
        return getImage() != null ? getImage().equals(equipment.getImage()) : equipment.getImage() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getPurchasedAt() != null ? getPurchasedAt().hashCode() : 0);
        result = 31 * result + (getBrand() != null ? getBrand().hashCode() : 0);
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + (getSerialNumber() != null ? getSerialNumber().hashCode() : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", type=" + type +
                ", purchasedAt=" + purchasedAt +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
