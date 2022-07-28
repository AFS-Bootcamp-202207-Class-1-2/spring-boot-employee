package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAll();

    Employee save(Employee employee);

    Employee findById(int id);

    List<Employee> findByGender(String gender);

    Page<Employee> findAll(Pageable pageable);
}
