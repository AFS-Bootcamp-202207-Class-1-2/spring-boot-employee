package com.rest.springbootemployee.service;


import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        Employee employee = new Employee(1, "A", 21, "male", 8000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.findAll();

        //then
        assertThat(actualEmployees, hasSize(1));
        assertThat(actualEmployees.get(0), equalTo(employee));
    }

    @Test
    public void should_return_employee_when_find_given_id() {
        //given
        Employee employee = new Employee(1, "A", 21, "male", 8000);
        int id = 1;
        given(employeeRepository.findEmployeeById(id)).willReturn(employee);

        //when
        Employee employeeById = employeeService.findEmployeeById(id);

        //then
        assertThat(employeeById, equalTo(employee));
    }

    @Test
    public void should_return_employee_when_find_given_gender() {
        //given
        Employee employeeA = new Employee(1, "A", 21, "male", 8000);
        Employee employeeB = new Employee(1, "A", 21, "female", 8000);
        Employee employeeC = new Employee(1, "A", 21, "male", 8000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeA);
        employees.add(employeeB);
        employees.add(employeeC);
        String gender = "male";
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        List<Employee> employeesByGender = employeeService.findEmployeesByGender(gender);

        //then
        assertThat(employeesByGender.get(0), equalTo(employeeA));
        assertThat(employeesByGender.get(1), equalTo(employeeC));
    }

    @Test
    public void should_return_employee_when_add_given_employee() {
        //given
        Employee employee = new Employee(1, "A", 21, "male", 8000);
        given(employeeRepository.addEmployee(employee)).willReturn(employee);

        //when
        Employee employeeNew = employeeService.addEmployee(employee);

        //then
        assertThat(employeeNew, equalTo(employee));
    }
}
