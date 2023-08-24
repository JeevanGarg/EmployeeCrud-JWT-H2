package com.employee.controller;

import com.employee.dtos.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @DisplayName(" JUnit test for create employee REST API")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{


        given(employeeService.createEmployee(any(EmployeeDto.class)))
                .willReturn(employeeDto);

        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));

    }

    @DisplayName(" JUnit test for Get All employees REST API")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{

        List<EmployeeDto> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(EmployeeDto.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build());
        listOfEmployees.add(EmployeeDto.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());

        given(employeeService.getAllEmoyees()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }


    @DisplayName("JUnit test for GET employee by id REST API")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{

        long employeeId = 1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(employeeDto);

        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{

        Long empId=1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();

        EmployeeDto updatedEmployee = EmployeeDto.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();

        given(employeeService.updateEmployee(empId,updatedEmployee))
                .willReturn(updatedEmployee);


        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    @DisplayName("JUnit test for delete employee REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployeeById(employeeId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
