package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.CompanyService;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmployeeService employeeService;

    private int companyId;

    @BeforeEach
    void DBclear() {
        employeeService.deleteAll();
        companyService.deleteAll();
        Company company = companyService.createCompany(new Company(1, "huawei", new ArrayList<>()));
        employeeService.addEmployee(new Employee(1, "zhangsan", 12, "male", 2532, company.getId()));
        companyId = company.getId();

    }

    @Test
    void should_get_all_company_when_perform_get_given_companies() throws Exception {
        //given
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(2532));

    }


    @Test
    void should_get_company_when_perform_get_given_id() throws Exception {
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/" + companyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(companyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(2532));

    }

    @Test
    void should_get_employees_under_certain_company_when_perform_get_given_id() throws Exception {
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/" + companyId + "/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(2532));

    }

    @Test
    void should_get_companies_when_perform_get_given_page_and_page_size() throws Exception {
        //given
        int page = 1;
        int pageSize = 1;
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies", page, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(companyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(2532));

    }

    @Test
    void should_add_companies_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"oracle\"\n" +
                "    }";
        //when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(companyId + 1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oracle"));
    }


    @Test
    void should_update_companies_when_perform_put_given_company_and_id() throws Exception {
        //given
        String updateCompany = "{\n" +
                "        \"id\": " + companyId + ",\n" +
                "        \"name\": \"oracle\"\n" +
                "    }";
        //when & then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCompany))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(companyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oracle"));
    }

    @Test
    void should_delete_company_when_perform_delete_given_id() throws Exception {

        //when & then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", companyId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(companyService.findAll(), hasSize(0));

    }
}
