package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

//    @Spy
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @BeforeEach
    void clear(){
        companyRepository.clearAll();
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        Company company = new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532))));

        List<Company> companies = new ArrayList<>();
        companies.add(company);
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<Company> actualCompanies = companyService.findAll();

        //then
        assertThat(actualCompanies, hasSize(1));
        assertThat(actualCompanies.get(0), equalTo(company));
    }

    @Test
    void should_return_company_when_find_given_id() {
        int id = 1;
        Company company = new Company(id, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532))));

        given(companyRepository.findById(id)).willReturn(company);

        //when
        Company actualCompany = companyService.findById(id);

        //then
        assertThat(actualCompany.getId(), equalTo(id));
    }

    @Test
    void should_return_employees_under_certain_company_when_find_given_id() {
        int id = 1;
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee(1, "zhangsan", 12, "male", 2532),
                new Employee(2, "lisi", 13, "male", 235)));

        given(companyRepository.findEmployeesUnderCertainCompany(id)).willReturn(employees);

        //when
        List<Employee> actualEmployees = companyService.findEmployeesUnderCertainCompany(id);

        //then
        assertThat(actualEmployees.get(0).getName(), equalTo("zhangsan"));
    }
}
