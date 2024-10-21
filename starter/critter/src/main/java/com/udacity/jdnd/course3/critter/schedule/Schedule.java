package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JoinTable(
            name = "schedule_employee", // Join table name
            joinColumns = @JoinColumn(name = "schedule_id"), // Foreign key for Schedule
            inverseJoinColumns = @JoinColumn(name = "employee_id") // Foreign key for Employee
    )
    private List<Employee> employees;
    @JoinTable(
            name = "schedule_pet", // Join table name
            joinColumns = @JoinColumn(name = "schedule_id"), // Foreign key for Schedule
            inverseJoinColumns = @JoinColumn(name = "pet_id") // Foreign key for Pet
    )
    private List<Pet> pets;
    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public Schedule() {
    }

    public Schedule(long id, List<Employee> employees, List<Pet> pets, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.employees = employees;
        this.pets = pets;
        this.date = date;
        this.activities = activities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
