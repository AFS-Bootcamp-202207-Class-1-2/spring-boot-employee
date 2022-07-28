package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.PushBuilder;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;

    @Autowired
    private JpaEmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee findEmployeeById(int id) {
        return Optional.ofNullable(employeeRepository.findById(id)).orElseThrow(NotFoundException::new);
    }


    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
//
//    public List<Employee> findEmployeeByPage(int page, int pageSize) {
//        return employeeRepository.findEmployeeByPage(page, pageSize);
//    }
//
//    public Employee updateEmployee(int id, Employee updateEmployee) {
//        return employeeRepository.updateEmployee(id, updateEmployee);
//    }
//
//    public void deleteEmployee(int id) {
//        employeeRepository.deleteEmployee(id);
//    }
}
