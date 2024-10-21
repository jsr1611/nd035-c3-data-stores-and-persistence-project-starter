package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

public interface ScheduleService {
    ScheduleDTO create(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> getAllSchedules();

    List<ScheduleDTO> getScheduleForPet(long petId);

    List<ScheduleDTO> getScheduleForEmployee(long employeeId);

    List<ScheduleDTO> getScheduleForCustomer(long customerId);
    
}
