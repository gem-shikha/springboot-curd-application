package com.springcurd.demo.service;

import com.springcurd.demo.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Employee addEmployee(Employee employee);

    Employee getEmployeeById(Integer id);

    Employee updateEmployee(Integer id, Employee employee);
    boolean deleteById(Integer id);
}
