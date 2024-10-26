package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT DISTINCT e from Employee e JOIN e.skills s WHERE s IN :skills")
    Set<Employee> findAllBySkills(@Param("skills") Set<EmployeeSkill> skills);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.daysAvailable d WHERE :dayOfWeek MEMBER OF d")
    Set<Employee> findAllByAvailability(@Param("dayOfWeek") DayOfWeek dayOfWeek);
}
