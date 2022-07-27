package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(int id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employeeRepository.findEmployeeByPage(page, pageSize);
    }

    public Employee updateEmployee(int id, Employee updateEmployee) {
        return employeeRepository.updateEmployee(id, updateEmployee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteEmployee(id);
    }
}
