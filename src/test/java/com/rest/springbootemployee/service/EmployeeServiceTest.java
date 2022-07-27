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
}
