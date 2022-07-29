package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

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

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return employeeRepository.findAll(pageRequest).toList();
    }

    public Employee updateEmployeeSalary(Employee updateEmployee) {
        Employee employeeById = findEmployeeById(updateEmployee.getId());
        merge(employeeById, updateEmployee);
        return employeeRepository.save(employeeById);
    }

    private void merge(Employee employeeById, Employee updateEmployee) {
        employeeById.setSalary(updateEmployee.getSalary());
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public void deleteAll(){
        employeeRepository.deleteAll();
    }
}
