package com.rest.springbootemployee;

import java.util.ArrayList;
import java.util.List;

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
}
