package com.springcurd.demo.controller;

import com.springcurd.demo.model.Employee;
import com.springcurd.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@OpenAPIDefinition(info = @Info(title = "Employee Controller", description = "Employee details Rest API"))
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;

    /**
     * Method to get the details of all the employee
     */
    @Operation(summary = "Get all employee details")
    @GetMapping
    ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> employeeList = employeeService.getAllEmployee();
        if (employeeList == null) {
            logger.error("No Employee details found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Employee details found : ", employeeList);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }


    /**
     * Method to fetch employee details by its id
     *
     * @param id
     */
    @Operation(summary = "Get all employee by its id")
    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            logger.error("Employee details not found by its id!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Employee details found by its id : {} ", employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    /**
     * Method to add new employee in the database
     *
     * @param employee
     */
    @Operation(summary = "Add an employee")
    @PostMapping
    ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee emp = employeeService.addEmployee(employee);
              if (emp == null) {
                logger.error("Employee details not created!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
                logger.info("Employee details created successfully : {} ",emp);
                return new ResponseEntity<>(emp, HttpStatus.OK);
      }

    /**
     *   Method to update the details of existing employee in the database
     * @param id
     * @param employee
     */
    @Operation(summary = "Update an employee by its id")
    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee){
        Employee emp = employeeService.updateEmployee(id, employee);
            if(emp == null){
                logger.warn("Employee details not updated!");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            logger.info("Employee details updated successfully");
        return new ResponseEntity<>(emp,HttpStatus.OK);

    }

    /**
     *   Method to delete an employee from the database using its id
     * @param id
     */
    @Operation(summary = "Delete an employee by its id")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable Integer id){
           if(employeeService.deleteById(id)) {
               return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
           }
        logger.warn("Employee Id does not exist");
        return new ResponseEntity<>("Employee Id does not exist", HttpStatus.NOT_FOUND);

    }
}
