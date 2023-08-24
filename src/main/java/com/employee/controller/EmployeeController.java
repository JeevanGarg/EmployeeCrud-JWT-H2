package com.employee.controller;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.ResourceNotFoundException;
import com.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${employee.common.path}")
public class EmployeeController
{
    private EmployeeService employeeService;
    private Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws ResourceNotFoundException {
        logger.info("Create Employee Method invoked");
        EmployeeDto employee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmoyees()
    {
        logger.info("Get All Employees Method invoked!");
        return new ResponseEntity<>(employeeService.getAllEmoyees(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        logger.info("Get Employee By Id Method invoked!");
        return new ResponseEntity<>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id,@Valid @RequestBody EmployeeDto employeeDto) throws ResourceNotFoundException {
        logger.info("update employee method invoked!");
        EmployeeDto employeeDto1 = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(employeeDto1,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        logger.info("Delete employee by Id invoked!");
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }
}
