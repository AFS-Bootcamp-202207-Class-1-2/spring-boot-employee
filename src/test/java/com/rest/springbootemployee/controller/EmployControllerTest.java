package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    EmployeeRepository employeeRepository;

    @BeforeEach
    void clearDB() {
        employeeRepository.clearAll();
    }

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given
        employeeRepository.addEmployee(new Employee(1, "Lisa", 29, "female", 9000));
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
        String newEmployee = "{\n" +
                "\"id\": 2, \n" +
                "\"name\": \"Lisa\", \n" +
                "\"age\": 21, \n" +
                "\"gender\": \"female\", \n" +
                "\"salary\": 6000\n" +
                "}";
        //when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(6000));
        //then
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(6000));
    }

    @Test
    void should_update_employee_when_perform_put_given_new_employee_and_id() throws Exception {
        //given
        List<Employee> employees = employeeRepository.findAll();
        employees.add(new Employee(1, "Lisa", 21, "female", 6000));
        int id = 1;
        String newEmployee = "{\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"li\",\n" +
                "            \"age\": 20,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 5000\n" +
                "        }";
        //when & then
        client.perform(MockMvcRequestBuilders.put("/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5000));

        //then

        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(5000));
    }

    @Test
    void should_delete_employee_when_perform_put_given_new_employee_and_id() throws Exception {
        //given
        List<Employee> employees = employeeRepository.findAll();
        employees.add(new Employee(1, "Lisa", 21, "female", 6000));
        int id = 1;
        //when & then
        client.perform(MockMvcRequestBuilders.delete("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(employees, hasSize(0));

    }

    @Test
    void should_get_employees_by_page_when_perform_put_given_page_and_page_size() throws Exception {
        //given
        List<Employee> employees = employeeRepository.findAll();
        employees.add(new Employee(1, "Lisa", 21, "female", 6000));
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

        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(21));
        assertThat(employees.get(0).getGender(), equalTo("female"));
        assertThat(employees.get(0).getSalary(), equalTo(6000));

    }

}
