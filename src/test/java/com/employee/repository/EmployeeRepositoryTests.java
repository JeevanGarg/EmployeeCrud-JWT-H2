package com.employee.repository;

import com.employee.entity.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests
{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTests(EmployeeRepository employeeRepository)
    {
        this.employeeRepository=employeeRepository;
    }

    private Employee employee;

    @BeforeEach
    public void setUp()
    {
        employee=Employee.builder()
                .firstName("Jeevan")
                .lastName("Garg")
                .email("jeevan.garg624@gmail.com")
                .build();
    }

    @DisplayName("Test for Save Employee")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee()
    {
        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    @DisplayName("Test for Get All Employees")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList()
    {
        Employee employee1=Employee.builder()
                .firstName("Rohit")
                .lastName("Garg")
                .email("rohit.garg624@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("Test for get Employee By Id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject()
    {
        employeeRepository.save(employee);

        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("Test for update Employee Operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee()
    {
        employeeRepository.save(employee);

        Employee employee1 = employeeRepository.findById(employee.getId()).get();
        employee1.setEmail("jeevan.garg89@gmail.com");
        employee1.setLastName("garg");

        Employee updatedEmployee = employeeRepository.save(employee1);

        assertThat(updatedEmployee.getEmail()).isEqualTo("jeevan.garg89@gmail.com");
        assertThat(updatedEmployee.getLastName()).isEqualTo("garg");

    }

    @DisplayName("Test for delete Employee Operation")
    @Test
    public void givenEmployeObject_whenDelete_thenRemoveEmployee()
    {
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());

        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("Test for get Employee by Email Operation")
    @Test
    public void givenEmployeEmail_whenFindByEmail_thenReturnEmployeeObject()
    {
        employeeRepository.save(employee);

        Employee employeeDB= employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employeeDB).isNotNull();
    }

     @DisplayName("Test for custom query using JPQL with index")
     @Test
     public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject()
     {
         employeeRepository.save(employee);
         String firstName = employee.getFirstName();
         String lastName = employee.getLastName();

         Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

         assertThat(savedEmployee).isNotNull();
     }

    @DisplayName("Test for custom query using JPQL with named Params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject()
    {
        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject()
    {
        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamed_thenReturnEmployeeObject()
    {
        employeeRepository.save(employee);
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(firstName, lastName);

        assertThat(savedEmployee).isNotNull();
    }
}
