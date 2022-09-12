/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.EmployeeDTO;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Employee;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade ef = EmployeeFacade.getEmployeeFacade(emf);
        ef.create(new EmployeeDTO(new Employee("Christoffer", "Knoesen 52", 100000)));
        ef.create(new EmployeeDTO(new Employee("Frank", "Sct jacobsvej", 2000000)));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
