package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.common.Utility;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @Override
    public ScheduleDTO create(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToEntity(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        return convertEntityToDTO(schedule);
    }
    private ScheduleDTO convertEntityToDTO(Schedule entity) {
        ScheduleDTO dto  = new ScheduleDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setActivities(entity.getActivities());

        if(entity.getPets() != null){
            List<Long> petIds = entity.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        if(entity.getEmployees() != null){
            List<Long> employeeIds = entity.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList());
            dto.setEmployeeIds(employeeIds);
        }
        return dto;
    }

    private Schedule convertDTOToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setDate(dto.getDate());
        if (!dto.getPetIds().isEmpty()) {
            List<Pet> pets = dto.getPetIds().stream().map(
                    petId -> petService.getPetById(petId)
            ).collect(Collectors.toList());
            schedule.setPets(pets);
        }
        if(!dto.getEmployeeIds().isEmpty()){
            List<Employee> employees = dto.getEmployeeIds().stream()
                    .map(emplId -> userService.getEmployeeById(emplId))
                    .collect(Collectors.toList());
            schedule.setEmployees(employees);
        }
        if(!dto.getActivities().isEmpty())
        {
            schedule.setActivities(dto.getActivities());
        }
        return schedule;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<ScheduleDTO> getScheduleForPet(long petId) {
        List<Schedule> all = scheduleRepository.findAll();
        List<ScheduleDTO> result = new ArrayList<>();
        Utility.getInstance().throwExceptionIf(all, "No schedules were found in the system. Please, add some first.");
        for (Schedule schedule : all) {
            for (Pet pet : schedule.getPets()) {
                if(pet.getId().equals(petId)){
                    result.add(convertEntityToDTO(schedule));
                    break;
                }
            }
        }
        Utility.getInstance().throwExceptionIf(result, "No schedules were found for the given pet id " + petId);
        return result;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> all = scheduleRepository.findAll();
        Utility.getInstance().throwExceptionIf(all, "No schedules were found in the system. Please, add some first.");
        List<ScheduleDTO> result = new ArrayList<>();
        for (Schedule schedule : all) {
            for (Employee employee : schedule.getEmployees()) {
                if(employee.getId().equals(employeeId)){
                    result.add(convertEntityToDTO(schedule));
                    break;
                }
            }
        }
        Utility.getInstance().throwExceptionIf(result, "No schedules were found for the given employee id " + employeeId);
        return result;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<Schedule> all = scheduleRepository.findAll();
        Utility.getInstance().throwExceptionIf(all, "No schedules were found in the system. Please, add some first.");
        List<ScheduleDTO> result = new ArrayList<>();
        for (Schedule schedule : all) {
            for (Pet pet : schedule.getPets()) {
                if(pet.getOwner().getId().equals(customerId)){
                    result.add(convertEntityToDTO(schedule));
                    break;
                }
            }
        }
        Utility.getInstance().throwExceptionIf(result, "No schedules were found for the given customer id " + customerId);
        return result;
    }
}
