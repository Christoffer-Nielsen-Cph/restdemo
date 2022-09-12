package facades;


import dtos.EmployeeDTO;
import entities.Employee;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public EmployeeDTO create(EmployeeDTO EmployeeDTO){
        Employee EmployeeEntity = new Employee(EmployeeDTO.getName(), EmployeeDTO.getAddress(),EmployeeDTO.getSalary());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(EmployeeEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new EmployeeDTO(EmployeeEntity);
    }
    public EmployeeDTO getById(long id) {
        EntityManager em = emf.createEntityManager();
        Employee Employee = em.find(Employee.class, id);
        return new EmployeeDTO(Employee);
    }

    
    public List<EmployeeDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        List<Employee> Employees = query.getResultList();
        return EmployeeDTO.getDtos(Employees);
    }

    public Employee getEmployeeByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.name = :employeeName", Employee.class);
        query.setParameter("employeeName", name);
        Employee employee = query.getResultList().get(0);
        return employee;
    }

    public List<Employee> getEmployeesWithHighestSalary() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e ORDER BY e.salary desc", Employee.class);
        List<Employee> employeesList = query.getResultList();
        return employeesList;
    }



    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade fe = getEmployeeFacade(emf);
        fe.getAll().forEach(dto->System.out.println(dto));
    }

}
