package com.springcurd.demo.service;

import com.springcurd.demo.model.Employee;
import com.springcurd.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl service;

    @Test
    void addEmployee() {
        Employee expectedResult = new Employee(1, "Shiva", "Product", "P1");
        when(employeeRepository.save(any())).thenReturn(expectedResult);
        Employee actualResult = service.addEmployee(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee(1,"Shivani","Product","P1");
        Employee employee1 = new Employee(2,"Tanuja","SDE","T1");
        employeeList.add(employee);
        employeeList.add(employee1);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualResult = service.getAllEmployee();
        assertEquals(employeeList.size(),actualResult.size());
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee(1,"Shivani","Product","P1");
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        Employee actualResult = service.getEmployeeById(employee.getEmpId());
        assertEquals(employee.getEmpId(),actualResult.getEmpId());

    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee(1,"Shivani","Product","P1");
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);
        Employee actualResult = service.getEmployeeById(employee.getEmpId());
        assertEquals(employee,actualResult);
    }

    @Test
    void deleteById() {
        Employee employee = new Employee(1,"Shivani","Product","P1");
        service.deleteById(employee.getEmpId());
        verify(employeeRepository,times(1)).deleteById(employee.getEmpId());
    }
}