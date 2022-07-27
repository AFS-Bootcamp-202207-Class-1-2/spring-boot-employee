package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;


    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(int id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findEmployeesUnderCertainCompany(int id) {
        return companyRepository.findEmployeesUnderCertainCompany(id);
    }

    public List<Company> findCompanyByPageAndPageSize(int page, int pageSize) {
        return companyRepository.findCompanyByPageAndPageSize(page, pageSize);
    }

    public Company createCompany(Company company) {
        return companyRepository.createCompany(company);
    }

    public Company updateCompanyById(int id, Company updateCompany) {
        return companyRepository.updateCompanyById(id, updateCompany);
    }

    public void deleteCompanyById(int id) {
        companyRepository.deleteCompanyById(id);
    }
}
