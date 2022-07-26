package com.rest.springbootemployee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JpaCompanyRepository companyRepository;

    private int companyId;

    @BeforeEach
    void clearDB() {
        employeeService.deleteAll();
        companyRepository.deleteAll();
        Company company = companyRepository.save(new Company(1, "hauwei", new ArrayList<>()));
        companyId = company.getId();
    }

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given

        employeeService.addEmployee(new Employee(1, "Lisa", 29, "female", 9000, companyId));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(29))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(9000));

    }




    @Test
    void should_create_new_employee_when_perform_post_given_new_employee() throws Exception {
        //given

        EmployeeRequestAdd employeeRequestAdd = new EmployeeRequestAdd();
        employeeRequestAdd.setAge(21);
        employeeRequestAdd.setName("Lisa");
        employeeRequestAdd.setGender("female");
        employeeRequestAdd.setSalary(6000);
        employeeRequestAdd.setCompanyId(companyId);

        ObjectMapper objectMapper = new ObjectMapper();
        String employeeString = objectMapper.writeValueAsString(employeeRequestAdd);

        //when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeString))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(6000));
        //then
        List<Employee> employees = employeeService.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(6000));
    }

    @Test
    void should_update_employee_when_perform_put_given_new_employee_and_id() throws Exception {
        //given

        Employee employee = employeeService.addEmployee(new Employee(1, "Lisa", 21, "female", 6000, companyId));
        int id = employee.getId();
        EmployeeRequestUpdate employeeRequestUpdate = new EmployeeRequestUpdate();
        employeeRequestUpdate.setId(id);
        employeeRequestUpdate.setSalary(5000);

        ObjectMapper objectMapper = new ObjectMapper();
        String updateEmployeeString = objectMapper.writeValueAsString(employeeRequestUpdate);
        //when & then
        client.perform(MockMvcRequestBuilders.put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5000));

        //then

        assertThat(employeeService.findAll(), hasSize(1));
        assertThat(employeeService.findAll().get(0).getSalary(), equalTo(5000));
    }

    @Test
    void should_delete_employee_when_perform_put_given_new_employee_and_id() throws Exception {
        //given
        Employee employee = employeeService.addEmployee(new Employee(1, "Lisa", 21, "female", 6000, companyId));
        int id = employee.getId();
        //when & then
        client.perform(MockMvcRequestBuilders.delete("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(employeeService.findAll(), hasSize(0));

    }

    @Test
    void should_get_employees_by_page_when_perform_put_given_page_and_page_size() throws Exception {
        //given
        employeeService.addEmployee(new Employee(1, "Lisa", 21, "female", 6000, companyId));
        int page = 1;
        int pageSize = 2;
        //when & then
        client.perform(MockMvcRequestBuilders.get("/employees", page, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", equalTo("Lisa")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", equalTo(21)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender", equalTo("female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary", equalTo(6000)));

        assertThat(employeeService.findAll(), hasSize(1));
        assertThat(employeeService.findAll().get(0).getName(), equalTo("Lisa"));
        assertThat(employeeService.findAll().get(0).getAge(), equalTo(21));
        assertThat(employeeService.findAll().get(0).getGender(), equalTo("female"));
        assertThat(employeeService.findAll().get(0).getSalary(), equalTo(6000));

    }

}
