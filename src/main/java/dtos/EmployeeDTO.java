/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Employee;
import entities.Person;

import java.util.ArrayList;
import java.util.List;
//
/**
 *
 * @author tha
 */
public class EmployeeDTO {
    private long id;
    private String name;

    private String address;

    private int salary;

    public EmployeeDTO(String name, String address, int salary) {
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public static List<EmployeeDTO> getDtos(List<Employee> employees){
        List<EmployeeDTO> employeeDtos = new ArrayList();
        employees.forEach(employee->employeeDtos.add(new EmployeeDTO(employee)));
        return employeeDtos;
    }


    public EmployeeDTO(Employee employee) {
        if(employee.getId() != null)
            this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
        this.salary = employee.getSalary();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}
