package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {

        companies.add(new Company(1, "huawei", new ArrayList<>(Arrays.asList(new Employee(1, "huang", 23, "male", 8000), new Employee(2, "zhang", 26, "male", 15005)))));
        companies.add(new Company(2, "google", new ArrayList<>(Arrays.asList(new Employee(3, "li", 20, "female", 8000), new Employee(4, "qian", 21, "male", 7989)))));
        companies.add(new Company(3, "apple", new ArrayList<>(Arrays.asList(new Employee(5, "xing", 23, "female", 9000), new Employee(6, "liang", 22, "female", 10000)))));

    }

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElseThrow(NotFoundException::new);
    }

    public List<Employee> findEmployeesUnderCertainCompany(int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElseThrow(NullPointerException::new).getEmployees();
    }

    public List<Company> findCompanyByPageAndPageSize(int page, int pageSize) {
        return companies.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Company createCompany(Company company) {
        company.setId(generateId());
        companies.add(company);
        return company;
    }

    public void updateCompanyById(int id, Company company) {
        Company companyToUpdate = findById(id);
        companyToUpdate.merge(company);
    }

    public void deleteCompanyById(int id) {
        Company company = findById(id);
        companies.remove(company);
    }

    public int generateId() {
       return companies.stream().mapToInt(company -> company.getId()).max().orElse(1);
    }

    public void clearAll() {
        companies.removeAll(companies);
    }
}
