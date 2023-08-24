package com.employee.service.impl;

import com.employee.controller.EmployeeController;
import com.employee.dtos.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,ModelMapper modelMapper)
    {
        this.employeeRepository=employeeRepository;
        this.modelMapper=modelMapper;
    }


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) throws ResourceNotFoundException
    {
        Employee employee = this.modelMapper.map(employeeDto, Employee.class);
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(optionalEmployee.isPresent())
        {
            throw new ResourceNotFoundException("Employee already exist with given email"+employee.getEmail());
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return this.modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) throws ResourceNotFoundException
    {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalEmployee.isPresent())
        {
            return new ModelMapper().map(optionalEmployee.get(),EmployeeDto.class);
        }
        else
        {
            throw new ResourceNotFoundException("Cannot find Employee By Id: "+employeeId);
        }
    }

    @Override
    public List<EmployeeDto> getAllEmoyees()
    {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDto> employeeDtoList = employeeList.stream().map(x ->new ModelMapper().map(x, EmployeeDto.class)).collect(Collectors.toList());
        return employeeDtoList;
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) throws ResourceNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalEmployee.isPresent())
        {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(employeeDto.getFirstName());
            employee.setEmail(employeeDto.getEmail());
            employee.setLastName(employeeDto.getLastName());
            Employee updatedEmployee = employeeRepository.save(employee);
            return new ModelMapper().map(updatedEmployee,EmployeeDto.class);
        }
        else
        {
            throw new ResourceNotFoundException("Cannot find Employee by Id: "+employeeId);
        }
    }

    @Override
    public void deleteEmployeeById(Long employeeId) throws ResourceNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if(optionalEmployee.isPresent())
        {
            employeeRepository.deleteById(employeeId);
        }
        else
        {
            throw new ResourceNotFoundException("Cannot find Employee by Id: "+employeeId);
        }
    }
}
