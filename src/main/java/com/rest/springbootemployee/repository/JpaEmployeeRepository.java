package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAll();

    Employee save(Employee employee);

    Employee findById(int id);
}
