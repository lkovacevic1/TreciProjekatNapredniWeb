package com.example.TreciProjekatNapredniWeb.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ErrorHistroy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;
    private String action;
    private String errorMessage;
    @ManyToOne(fetch = FetchType.EAGER)
    private Machine machine;

    public ErrorHistroy() {
    }

    public ErrorHistroy(Long id, LocalDateTime date, String action, String errorMessage, Machine machine) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.errorMessage = errorMessage;
        this.machine = machine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
