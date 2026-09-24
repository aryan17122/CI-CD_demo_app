package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void shouldCreateEmployee() {

        // Arrange
        Employee employee =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        // Act
        Employee result = employeeService.createEmployee(employee);

        // Assert
        assertEquals(employee, result);

        // Verify
        verify(employeeRepository).save(employee);
    }
    @Test
    void shouldGetAllEmployees() {

        // Arrange
        Employee employee1 =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        Employee employee2 =
                new Employee("Rahul", "rahul@gmail.com", "HR");

        List<Employee> employees =
                List.of(employee1, employee2);

        when(employeeRepository.findAll())
                .thenReturn(employees);

        // Act
        List<Employee> result =
                employeeService.getAllEmployees();

        // Assert
        assertEquals(employees, result);

        // Verify
        verify(employeeRepository).findAll();
    }
    @Test
    void shouldGetEmployeeById() {

        // Arrange
        Employee employee =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        when(employeeRepository.findById(1L))
                .thenReturn(java.util.Optional.of(employee));

        // Act
        Employee result =
                employeeService.getEmployeeById(1L);

        // Assert
        assertEquals(employee, result);

        // Verify
        verify(employeeRepository).findById(1L);
    }
    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        // Act + Assert
        RuntimeException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class,
                        () -> employeeService.getEmployeeById(1L)
                );

        // Verify
        assertEquals(
                "Employee not found with id: 1",
                exception.getMessage()
        );

        verify(employeeRepository).findById(1L);
    }
    @Test
    void shouldDeleteEmployee() {

        // Act
        employeeService.deleteEmployee(1L);

        // Verify
        verify(employeeRepository).deleteById(1L);
    }
}