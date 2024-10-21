package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface UserService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    Employee getEmployeeById(long employeeId);
    EmployeeDTO getEmployeeDTOById(long employeeId);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeDTO);

    Customer getCustomerById(long ownerId);

    Customer convertDTOToEntity(CustomerDTO dto);

    CustomerDTO convertEntityToDTO(Customer entity);

    EmployeeDTO convertEntityToDTO(Employee entity);

    Employee convertDTOToEntity(EmployeeDTO dto);
}
