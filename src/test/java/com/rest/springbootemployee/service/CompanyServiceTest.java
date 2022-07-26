package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {


    @Mock
//    @Spy
    JpaCompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;


    @BeforeEach
    void clear(){
        companyService.deleteAll();
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        Company company = new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1))));

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
        Company company = new Company(id, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "zhangsan", 12, "male", 2532, 1))));

        given(companyRepository.findById(id)).willReturn(company);

        //when
        Company actualCompany = companyService.findById(id);

        //then
        assertThat(actualCompany.getId(), equalTo(id));
    }

    // todo
    @Test
    void should_return_employees_under_certain_company_when_find_given_id() {
        int id = 1;
        Company company = new Company(1, "huawei", new ArrayList<>());
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee(1, "zhangsan", 12, "male", 2532, 1),
                new Employee(2, "lisi", 13, "male", 235, 1)));
        company.setEmployees(employees);

        given(companyRepository.findById(id)).willReturn(company);

        //when
        List<Employee> actualEmployees = companyService.findEmployeesUnderCertainCompany(id);

        //then
        assertThat(actualEmployees.get(0).getName(), equalTo(employees.get(0).getName()));
    }

    @Test
    void should_return_companies_when_find_given_page_and_page_size() {
        int page = 1;
        int pageSize = 1;

        List<Company> companiesByPage = new ArrayList<>();
        companiesByPage.add(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "huang", 23, "male", 8000, 1), new Employee(2, "zhang", 26, "male", 15005, 1)))));
        companiesByPage.add(new Company(2, "google", new ArrayList<>(Arrays.asList(new Employee(3, "li", 20, "female", 8000, 1), new Employee(4, "qian", 21, "male", 7989, 1)))));
        companiesByPage.add(new Company(3, "apple", new ArrayList<>(Arrays.asList(new Employee(5, "xing", 23, "female", 9000, 1), new Employee(6, "liang", 22, "female", 10000, 1)))));



        Page pageOfEmployees = new PageImpl(companiesByPage);
        given(companyRepository.findAll(PageRequest.of(page, pageSize))).willReturn(pageOfEmployees);

        //when
        List<Company> actualCompanies = companyService.findCompanyByPageAndPageSize(page, pageSize);

        //then
        assertThat(actualCompanies.get(0).getName(), equalTo(companiesByPage.get(0).getName()));

    }

    @Test
    void should_return_company_when_add_given_company() {

        Company company = new Company(1, "apple", new ArrayList<>(Arrays.asList(new Employee(5, "xing", 23, "female", 9000, 1), new Employee(6, "liang", 22, "female", 10000, 1))));

        given(companyRepository.save(company)).willReturn(company);

        //when
        Company newCompany = companyService.createCompany(company);

        //then
        assertThat(newCompany.getId(), equalTo(company.getId()));
        assertThat(newCompany.getName(), equalTo(company.getName()));
        assertThat(newCompany.getEmployees().get(0).getName(), equalTo(company.getEmployees().get(0).getName()));

    }

    @Test
    void should_return_company_when_update_given_company_and_id() {
        int id = 1;
        Company originCompany = new Company(id, "google", new ArrayList<>(Arrays.asList(new Employee(5, "xing", 23, "female", 9000, 1), new Employee(6, "liang", 22, "female", 10000, 1))));
        Company updateCompany = new Company(id, "apple", new ArrayList<>(Arrays.asList(new Employee(5, "xing", 23, "female", 9000, 1), new Employee(6, "liang", 22, "female", 10000, 1))));


        given(companyRepository.findById(id)).willReturn(originCompany);

        //when
        companyService.updateCompanyById(id, updateCompany);

        //then
        verify(companyRepository).save(updateCompany);
    }

    @Test
    void should_return_no_content_when_delete_given_id() {
        //given
        int id = 1;
        given(companyRepository.findById(1)).willReturn(new Company());

        //when
        companyService.deleteCompanyById(id);

        //then
        verify(companyRepository).deleteById(id);
    }
}
