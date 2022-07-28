package com.rest.springbootemployee.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = Employee.class)
    @JoinColumn(name = "companyId")
    private List<Employee> employees = new ArrayList<>();


    public Company(int id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }



    public void merge(Company company) {
        this.name = company.getName();
        this.employees.clear();
        this.employees.addAll(company.getEmployees());
    }


}
