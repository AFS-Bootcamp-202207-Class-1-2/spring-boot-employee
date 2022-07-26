package com.rest.springbootemployee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1, "huang", 23, "male", 8000));
        employees.add(new Employee(2, "zhang", 23, "male", 8000));
        employees.add(new Employee(3, "li", 23, "female", 8000));
        employees.add(new Employee(4, "qian", 23, "male", 7989));
        employees.add(new Employee(5, "xing", 23, "female", 9000));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        employee.setId(generateNewId());
        employees.add(employee);
        return employee;
    }

    private int generateNewId() {
        int maxId = employees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(1);
        return maxId + 1;
    }


    public Employee updateEmployee(int id, Employee employee) {
        Employee employeeToUpdate = findEmployeeById(id);
        employeeToUpdate.merge(employee);
        return employeeToUpdate;
    }


    public List<Employee> deleteEmployee(int id) {
        Employee employeeToDelete = findEmployeeById(id);
        employees.remove(employeeToDelete);
        return employees;
    }

    public List<Employee> findEmployeeByPage(int page, int pageSize) {
        return employees.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

    }
}
