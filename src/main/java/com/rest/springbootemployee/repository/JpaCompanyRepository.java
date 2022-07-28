package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.domain.Company;
import com.rest.springbootemployee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {


    Company findById(int id);

}
