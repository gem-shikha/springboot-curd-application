package com.springcurd.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcurd.demo.model.Employee;
import com.springcurd.demo.service.EmployeeService;
import com.springcurd.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    EmployeeServiceImpl employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployee() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(19, "Shailja", "SDE", "SDE1"));
        employeeList.add(new Employee(27, "Shikha", "Dev", "E1"));
        when(employeeService.getAllEmployee()).thenReturn(employeeList);
        this.mockMvc
                .perform(get("/employee"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void getAllEmployee_Negative() throws Exception {
       List<Employee> employeeList = null;
        when(employeeService.getAllEmployee()).thenReturn(employeeList);
        this.mockMvc
                .perform(get("/employee"))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee(1, "John", "Admin", "A1");
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        this.mockMvc
                .perform(get("/employee/{id}", employee.getEmpId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".empId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath(".department").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath(".grade").value("A1"))
              .andDo(print());
    }

    @Test
    void addEmployee() throws Exception {
        Employee employee = new Employee(27, "Shikha", "Dev", "E1");
        when(employeeService.addEmployee(any())).thenReturn(employee);
        ObjectMapper mapper = new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(employee);

        this.mockMvc
                .perform(
                        post("/employee")
                                .content(jsonbody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".empId").value(27))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Shikha"))
                .andExpect(MockMvcResultMatchers.jsonPath(".department").value("Dev"))
                .andExpect(MockMvcResultMatchers.jsonPath(".grade").value("E1"))
                .andDo(print());

    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(2, "Joo", "Dev", "E1");
        when(employeeService.updateEmployee(anyInt(), any())).thenReturn(employee);
        ObjectMapper mapper = new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(employee);

        this.mockMvc
                .perform(
                        put("/employee/{id}", employee.getEmpId())
                                .content(jsonbody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".empId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Joo"))
                .andExpect(MockMvcResultMatchers.jsonPath(".department").value("Dev"))
                .andExpect(MockMvcResultMatchers.jsonPath(".grade").value("E1"))
                .andDo(print());
    }

    @Test
    void deleteById() throws Exception {
        int id = 2;
        boolean status =  true;
        when(employeeService.deleteById(id)).thenReturn(status);
        mockMvc.perform(delete("/employee/{id}",id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

/*

    @Test
    void get_AllEmployee() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(19,"Shailja","SDE","SDE1"));
        employeeList.add(new Employee(27,"Shikha","Dev","E1"));
        when(employeeService.getAllEmployee()).thenReturn(employeeList);
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployee();
        assertEquals(HttpStatus.FOUND,response.getStatusCode());
        assertEquals(2, response.getBody());
    }

    @Test
    void get_EmployeeById() {
        employee = new Employee(19,"Shailja","SDE","SDE1");
        when(employeeService.getEmployeeById(employee.getEmpId())).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.getEmployeeById(employee.getEmpId());
        assertEquals(HttpStatus.FOUND,res.getStatusCode());
        assertEquals(employee.getEmpId(),res.getBody().getEmpId());
    }
*/

