package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.common.NotFoundException;
import com.udacity.jdnd.course3.critter.common.Utility;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PetService petService;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = convertDTOToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return convertEntityToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return convertEntityToDTO(employee);
    }

    @Override
    public Employee getEmployeeById(long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        return optionalEmployee.orElse(null);
    }

    @Override
    public EmployeeDTO getEmployeeDTOById(long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee != null) return convertEntityToDTO(employee);
        else throw new NotFoundException("No employee was found with given id: " + employeeId);
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        log.info("setting employee schedule");
        Employee employee = null;
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
            log.info("Employee schedule was set successfully");
        } else {
            throw new NotFoundException("Employee not found with id: " + employeeId);
        }
    }

    @Override
    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeDTO) {
        log.info("Request for a list of employees that meet the request details");
        LocalDate date = employeeDTO.getDate();
        DayOfWeek dayOfWeek = DayOfWeek.from(date);
        Set<Employee> employees = employeeRepository.findAllByAvailability(dayOfWeek);
        Utility.getInstance().throwExceptionIf(employees, "No employee found available for the given date and activities");
         return employees
                .stream()
                .filter(entity -> entity.getSkills().containsAll(employeeDTO.getSkills()))
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public Customer convertDTOToEntity(CustomerDTO dto) {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setNotes(dto.getNotes());

        // Find the pets by their ids and set them to the customer
        if (dto.getPetIds() != null) {
            List<Pet> pets = dto.getPetIds().stream()
                    .map(petService::getPetById)
                    .collect(Collectors.toList());
            entity.setPets(pets);
        }
        return entity;
    }

    @Override
    public CustomerDTO convertEntityToDTO(Customer entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNotes(entity.getNotes());
        dto.setPhoneNumber(entity.getPhoneNumber());
        if (entity.getPets() != null) {
            List<Long> petIds = entity.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        return dto;
    }

    @Override
    public EmployeeDTO convertEntityToDTO(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSkills(entity.getSkills());
        dto.setDaysAvailable(entity.getDaysAvailable());
        return dto;
    }

    @Override
    public Employee convertDTOToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSkills(dto.getSkills());
        employee.setDaysAvailable(dto.getDaysAvailable());
        return employee;
    }
}
