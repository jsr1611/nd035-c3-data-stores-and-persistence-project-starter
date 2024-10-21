package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.common.Creature;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
public class Employee extends Creature {
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> skills;
    @ElementCollection(targetClass = DayOfWeek.class)
    private Set<DayOfWeek> daysAvailable;

    public Employee(){}

    public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        super(id, name);
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
