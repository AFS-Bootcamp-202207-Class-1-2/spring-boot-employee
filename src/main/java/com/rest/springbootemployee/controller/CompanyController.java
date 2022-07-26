package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping()
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesUnderCertainCompany(@PathVariable int id) {
        return companyRepository.findEmployeesUnderCertainCompany(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPageAndPageSize(@RequestParam int page, @RequestParam int pageSize) {
        return companyRepository.findCompanyByPageAndPageSize(page, pageSize);
    }
}
