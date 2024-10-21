package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.common.Creature;
import com.udacity.jdnd.course3.critter.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Pet extends Creature {
    private PetType type;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer owner;
    private LocalDate birthDate;
    private String notes;

    public Pet(){}

    public Pet(Long id, PetType type, String name, Customer owner, LocalDate birthDate, String notes) {
        super(id, name);
        this.type = type;
        this.owner = owner;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
