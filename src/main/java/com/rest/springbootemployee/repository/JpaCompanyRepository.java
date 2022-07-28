package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {
}
