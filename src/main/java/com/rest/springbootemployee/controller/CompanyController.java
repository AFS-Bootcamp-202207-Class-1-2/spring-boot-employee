package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public List<Company> getCompanies() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesUnderCertainCompany(@PathVariable int id) {
        return companyService.findEmployeesUnderCertainCompany(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPageAndPageSize(@RequestParam int page, @RequestParam int pageSize) {
        return companyService.findCompanyByPageAndPageSize(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

//    @PutMapping("/{id}")
//    public Company updateCompany(@PathVariable int id, @RequestBody Company company) {
//        return companyService.updateCompanyById(id, company);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCompanyById(@PathVariable int id) {
//        companyService.deleteCompanyById(id);
//    }
}
