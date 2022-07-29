package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable int id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping(params = "gender")
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeService.findEmployeesByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequestAdd employeeRequestAdd) {
        Employee employee = employeeMapper.requestToEntity(employeeRequestAdd);
        Employee addEmployee = employeeService.addEmployee(employee);
        return employeeMapper.entityToResponse(addEmployee);
    }

    @PutMapping()
    public EmployeeResponseUpdate updateEmployeesById(@RequestBody EmployeeRequestUpdate employeeRequestUpdate) {
        Employee employee = employeeMapper.requestToEntity(employeeRequestUpdate);
        Employee updatedEmployee = employeeService.updateEmployeeSalary(employee);
        return employeeMapper.entityToResponseUpdate(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize) {
        return employeeService.findEmployeeByPage(page, pageSize);
    }
}
