package com.rest.springbootemployee.controller.mapper;

import com.rest.springbootemployee.controller.EmployeeRequestAdd;
import com.rest.springbootemployee.controller.EmployeeRequestUpdate;
import com.rest.springbootemployee.controller.EmployeeResponse;
import com.rest.springbootemployee.controller.EmployeeResponseUpdate;
import com.rest.springbootemployee.domain.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee requestToEntity(EmployeeRequestAdd employeeRequestAdd) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequestAdd, employee);
        return employee;
    }

    public Employee requestToEntity(EmployeeRequestUpdate employeeRequestUpdate) {
        Employee employee = new Employee();
        employee.setId(employeeRequestUpdate.getId());
        employee.setSalary(employeeRequestUpdate.getSalary());
        return employee;
    }

    public EmployeeResponse entityToResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    public EmployeeResponseUpdate entityToResponseUpdate(Employee employee) {
        EmployeeResponseUpdate employeeResponse = new EmployeeResponseUpdate();
        employeeResponse.setSalary(employee.getSalary());
        return employeeResponse;
    }

}
