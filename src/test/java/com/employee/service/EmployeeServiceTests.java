package com.employee.service;

import com.employee.dtos.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests
{
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private ModelMapper modelMapper;

    private Employee employee;

    private EmployeeDto employeeDto;

    @BeforeEach
    public void setUp()
    {
        employee= Employee.builder()
                .firstName("Jeevan")
                .lastName("garg")
                .email("jeevan.garg624@gmail.com")
                .build();

        employeeDto= EmployeeDto.builder()
                .firstName("Jeevan")
                .lastName("garg")
                .email("jeevan.garg624@gmail.com")
                .build();
    }



    @DisplayName("Test for save Employee Method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws ResourceNotFoundException {

        doReturn(employee).when(modelMapper).map(employeeDto, Employee.class);
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee))
                .willReturn(employee);


        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);

        assertThat(savedEmployee).isNotNull();

    }


    @DisplayName("Test for save Existing Employee Method")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException()
    {
        doReturn(employee).when(modelMapper).map(employeeDto, Employee.class);
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.createEmployee(employeeDto);
        });

        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @DisplayName("Test for get All Employees")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList()
    {
        Employee employee1=Employee.builder()
                .firstName("Rohit")
                .lastName("garg")
                .email("rohit.garg624@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        List<EmployeeDto> employeeDtoList = employeeService.getAllEmoyees();

        assertThat(employeeDtoList).isNotNull();
        assertThat(employeeDtoList.size()).isEqualTo(2);
    }

    @DisplayName("Test for get Employee By Id")
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() throws ResourceNotFoundException {
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        EmployeeDto savedEmployee = employeeService.getEmployeeById(employee.getId());

        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Test for get Employee By Id Exception")
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenThrowsException()
    {
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.getEmployeeById(employee.getId());
        });

    }

    @DisplayName("Test for updateEmployee Method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() throws ResourceNotFoundException {

        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        employee.setEmail("jeevan.garg89@gmail.com");
        employee.setFirstName("Jiwan");
        employeeDto.setEmail("jeevan.garg89@gmail.com");
        employeeDto.setFirstName("Jiwan");
        given(employeeRepository.save(employee)).willReturn(employee);

        EmployeeDto updatedEmployee = employeeService.updateEmployee(employee.getId(), employeeDto);

        assertThat(updatedEmployee.getEmail()).isEqualTo("jeevan.garg89@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Jiwan");
    }

    @DisplayName("Test for update Employee Exception")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenThrowsException()
    {
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.updateEmployee(employee.getId(),employeeDto);
        });

        //verify(employeeRepository,never()).save(any(Employee.class));
    }

    @DisplayName("Test for delete Employee Method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() throws ResourceNotFoundException {
        willDoNothing().given(employeeRepository).deleteById(employee.getId());
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        employeeService.deleteEmployeeById(employee.getId());

        verify(employeeRepository,times(1)).deleteById(employee.getId());
    }

    @DisplayName("Test for get Employee By Id Exception")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenThrowsException()
    {
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.deleteEmployeeById(employee.getId());
        });

    }

}
