package com.example.demo;

import com.example.demo.controller.EmployeeController;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @Test
    void shouldGetAllEmployees() throws Exception {

        // Arrange
        Employee employee1 =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        Employee employee2 =
                new Employee("Rahul", "rahul@gmail.com", "HR");

        when(employeeService.getAllEmployees())
                .thenReturn(List.of(employee1, employee2));

        // Act + Assert
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Aryan"))
                .andExpect(jsonPath("$[1].name").value("Rahul"));
    }
    @Test
    void shouldGetEmployeeById() throws Exception {

        // Arrange
        Employee employee =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        // Act + Assert
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aryan"))
                .andExpect(jsonPath("$.email").value("aryan@gmail.com"))
                .andExpect(jsonPath("$.department").value("IT"));
    }
    @Test
    void shouldCreateEmployee() throws Exception {

        // Arrange
        Employee employee =
                new Employee("Aryan", "aryan@gmail.com", "IT");

        when(employeeService.createEmployee(org.mockito.ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);

        // Act + Assert
        mockMvc.perform(
                        post("/api/employees")
                                .contentType("application/json")
                                .content("""
                                    {
                                        "name": "Aryan",
                                        "email": "aryan@gmail.com",
                                        "department": "IT"
                                    }
                                    """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aryan"))
                .andExpect(jsonPath("$.email").value("aryan@gmail.com"))
                .andExpect(jsonPath("$.department").value("IT"));
    }
    @Test
    void shouldDeleteEmployee() throws Exception {

        // Act + Assert
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));

        // Verify
        verify(employeeService).deleteEmployee(1L);
    }
}