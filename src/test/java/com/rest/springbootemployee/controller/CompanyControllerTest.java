package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    CompanyRepository companyRepository;


    @BeforeEach
    void DBclear() {
        companyRepository.clearAll();
    }

    @Test
    void should_get_all_company_when_perform_get_given_companies() throws Exception {
        //given
        companyRepository.createCompany(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1)))));
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(2532));

    }

    @Test
    void should_get_company_when_perform_get_given_id() throws Exception {
        //given
        companyRepository.createCompany(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1)))));
        int id = 1;
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(2532));

    }

    @Test
    void should_get_employees_under_certain_company_when_perform_get_given_id() throws Exception {
        //given
        companyRepository.createCompany(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1)))));
        int id = 1;
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/" + id + "/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(2532));

    }

    @Test
    void should_get_companies_when_perform_get_given_page_and_page_size() throws Exception {
        //given
        companyRepository.createCompany(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1)))));
        int page = 1;
        int pageSize = 1;
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies", page, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("huawei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("zhangsan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(2532));

    }

    @Test
    void should_add_companies_when_perform_post_given_page_and_page_size() throws Exception {
        //given
        String company = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"oracle\",\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 5,\n" +
                "                \"name\": \"xing\",\n" +
                "                \"age\": 23,\n" +
                "                \"gender\": \"female\",\n" +
                "                \"salary\": 9000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 6,\n" +
                "                \"name\": \"liang\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"female\",\n" +
                "                \"salary\": 10000\n" +
                "            }\n" +
                "        ]\n" +
                "    }";
        //when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oracle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("xing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(9000));
    }


    @Test
    void should_update_companies_when_perform_put_given_company_and_id() throws Exception {
        //given
        int id = 1;
        Company originCompany = new Company(id, "oracle", new ArrayList<>(Arrays.asList(
                new Employee(5, "xing", 23, "female", 1000, 1),
                new Employee(6, "liang", 22, "female", 10000, 1)
        )));

        companyRepository.createCompany(originCompany);

        String company = "{\n" +
                "        \"id\": " + id + ",\n" +
                "        \"name\": \"oracle\",\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 5,\n" +
                "                \"name\": \"xing\",\n" +
                "                \"age\": 23,\n" +
                "                \"gender\": \"female\",\n" +
                "                \"salary\": 9000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 6,\n" +
                "                \"name\": \"liang\",\n" +
                "                \"age\": 22,\n" +
                "                \"gender\": \"female\",\n" +
                "                \"salary\": 10000\n" +
                "            }\n" +
                "        ]\n" +
                "    }";
        //when & then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("oracle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("xing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(9000));
    }

    @Test
    void should_delete_company_when_perform_delete_given_id() throws Exception {
        //given
        List<Company> companies = companyRepository.findAll();
        int id = 1;
        Company company = new Company(id, "oracle", new ArrayList<>(Arrays.asList(
                new Employee(6, "liang", 22, "female", 10000, 1)
        )));
        companies.add(company);
        //when & then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertThat(companies, hasSize(0));

    }
}
