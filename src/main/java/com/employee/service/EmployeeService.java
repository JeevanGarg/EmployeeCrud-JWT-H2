package com.employee.service;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService
{
    EmployeeDto createEmployee(EmployeeDto employeeDto) throws ResourceNotFoundException;

    EmployeeDto getEmployeeById(Long employeeId) throws ResourceNotFoundException;

    List<EmployeeDto> getAllEmoyees();

    EmployeeDto updateEmployee(Long employeeId,EmployeeDto employeeDto) throws ResourceNotFoundException;

    void deleteEmployeeById(Long employeeId) throws ResourceNotFoundException;
}
