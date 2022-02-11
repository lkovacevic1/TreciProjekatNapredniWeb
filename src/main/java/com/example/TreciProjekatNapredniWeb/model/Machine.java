package com.example.TreciProjekatNapredniWeb.model;

import com.example.TreciProjekatNapredniWeb.model.enums.Status;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;
    private Boolean active;
    private String name;
    private LocalDate created;
    @Version
    private Integer version;
    private boolean isUsed = false;

    public Machine() {
    }

    public Machine(Long id, Status status, User createdBy, Boolean active) {
        this.id = id;
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
    }

    public Machine(Long id, Status status, User createdBy, Boolean active, String name, LocalDate created) {
        this.id = id;
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
        this.name = name;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setName(String name){ this.name = name; }

    public String getName() { return name; }

    public void setCreated(LocalDate created) { this.created = created; }

    public LocalDate getCreated() { return created; } public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
