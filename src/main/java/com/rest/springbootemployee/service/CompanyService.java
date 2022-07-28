package com.rest.springbootemployee.service;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

//    @Autowired
//    CompanyRepository companyRepository;

    @Autowired
    JpaCompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void deleteAll() {
        companyRepository.deleteAll();
    }

    public Company findById(int id) {
        return Optional.ofNullable(companyRepository.findById(id)).orElseThrow(NotFoundException::new);
    }
//
//    public List<Employee> findEmployeesUnderCertainCompany(int id) {
//        return companyRepository.findEmployeesUnderCertainCompany(id);
//    }
//
//    public List<Company> findCompanyByPageAndPageSize(int page, int pageSize) {
//        return companyRepository.findCompanyByPageAndPageSize(page, pageSize);
//    }
//
//    public Company createCompany(Company company) {
//        return companyRepository.createCompany(company);
//    }
//
//    public Company updateCompanyById(int id, Company updateCompany) {
//        return companyRepository.updateCompanyById(id, updateCompany);
//    }
//
//    public void deleteCompanyById(int id) {
//        companyRepository.deleteCompanyById(id);
//    }
//

}
