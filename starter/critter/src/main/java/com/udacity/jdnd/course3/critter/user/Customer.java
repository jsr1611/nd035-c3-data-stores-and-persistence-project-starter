package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.common.Creature;
import com.udacity.jdnd.course3.critter.pet.Pet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class Customer extends Creature {

    private String phoneNumber;
    private String notes;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("owner")
    private List<Pet> pets;


    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        super(id, name);
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public Customer() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
