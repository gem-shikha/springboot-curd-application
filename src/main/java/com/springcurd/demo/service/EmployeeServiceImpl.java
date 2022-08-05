package com.springcurd.demo.service;

import com.springcurd.demo.model.Employee;
import com.springcurd.demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Method to get all the employees
     */
    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        try {
            employeeList = employeeRepository.findAll();
            log.info("getAllEmployee() {} :", employeeList);
        } catch (Exception e) {
            log.error("Exception in getAllEmployee() : {}", e.getMessage());
        }
        return employeeList;
    }

    /**
     * Method to add new employee in the database
     *
     * @param employee
     */
    @Override
    public Employee addEmployee(final Employee employee) {
        Employee emp = null;
        try {
            emp = employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Exception in addEmployee() {} :", e.getMessage());
        }
        return emp;
    }

    /**
     * Method to fetch employee details by its id
     *
     * @param id
     */
    @Override
    public Employee getEmployeeById(final Integer id) {
        Employee employee = null;
        try {
            if (id != null) {
                employee = employeeRepository.findById(id).get();
            }
            log.debug("Employee getEmpById() : {} ", employee);
        } catch (Exception e) {
            log.error("Exception in empGetById() : {} ", e.getMessage());
        }
        return employee;
    }

    /**
     *   Method to update the details of existing employee in the database
     * @param id
     * @param employee
     */
    @Override
    public Employee updateEmployee(final Integer id, final Employee employee) {
        Employee empObj = employeeRepository.findById(id).get();
        if (empObj == null) {
            log.error("Exception in updateEmployee() : {} ");
            return empObj;
        }
        empObj.setName(employee.getName());
        empObj.setDepartment(employee.getDepartment());
        empObj.setGrade(employee.getGrade());
        return employeeRepository.save(empObj);
    }

    /**
     *   Method to delete an employee from the database using its id
     * @param id
     */
    @Override
    public boolean deleteById(final Integer id) {
        try {
            employeeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Exception in deleteEmployee() : {} ", e.getMessage());
            return false;
        }
    }

}
