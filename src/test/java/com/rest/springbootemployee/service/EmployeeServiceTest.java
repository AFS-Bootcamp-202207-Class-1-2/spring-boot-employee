package com.rest.springbootemployee.service;


import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
//    @Mock
    @Spy
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void clear(){
        employeeRepository.clearAll();
    }

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
        Employee employeeUpdated = new Employee(1, "A", 22, "male", 8000);
        given(employeeRepository.addEmployee(employee)).willReturn(employee);

        //when
        Employee actualEmployee = employeeService.addEmployee(employee);

        //then
        assertThat(actualEmployee, equalTo(employee));
    }

    @Test
    public void should_return_employee_by_page_when_get_given_page_pageSize() {
        //given
        Employee employeeA = new Employee(1, "A", 21, "male", 8000);
        Employee employeeB = new Employee(1, "A", 21, "female", 8000);
        Employee employeeC = new Employee(1, "A", 21, "male", 8000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeA);
        employees.add(employeeB);
        employees.add(employeeC);

        List<Employee> employeesByPage = new ArrayList<>();
        employeesByPage.add(employeeA);
        employeesByPage.add(employeeB);
        int page = 1;
        int pageSize = 1;

        given(employeeRepository.findEmployeeByPage(page, pageSize)).willReturn(employeesByPage);

        //when
        List<Employee> actualEmployeesByPage = employeeService.findEmployeeByPage(page, pageSize);

        //then
        assertThat(actualEmployeesByPage.get(0), equalTo(employeeA));
        assertThat(actualEmployeesByPage.get(1), equalTo(employeeB));
    }


    @Test
    public void should_return_employee_when_update_given_id_and_employee() {
        //given
        int id = 1;
        Employee employee = new Employee(id, "A", 21, "male", 8000);
        Employee updateEmployee = new Employee(id, "A", 21, "male", 10000);

        given(employeeRepository.findAll()).willCallRealMethod();
        given(employeeRepository.updateEmployee(id, updateEmployee)).willCallRealMethod();
        List<Employee> employees = employeeService.findAll();
        employees.add(employee);

        //when
        Employee updatedEmployee = employeeService.updateEmployee(id, updateEmployee);

        //then
//        verify(employeeRepository).updateEmployee(1, updateEmployee);
        assertThat(updatedEmployee.getSalary(), equalTo(updateEmployee.getSalary()));
    }

    @Test
    public void should_return_no_content_when_delete_given_id() {
        //given
        Employee employee = new Employee(1, "A", 21, "male", 8001);
        employeeService.addEmployee(employee);

        //when
        employeeService.deleteEmployee(1);

        //then
        verify(employeeRepository).deleteEmployee(1);
    }

}
