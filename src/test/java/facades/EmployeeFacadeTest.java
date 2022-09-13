package facades;

import dtos.EmployeeDTO;
import entities.Employee;
import entities.RenameMe;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
//
public class EmployeeFacadeTest {

    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;

    public EmployeeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = EmployeeFacade.getEmployeeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Employee.deleteAllRows").executeUpdate();
            em.persist(new Employee("Frank","Knoesen",1000));
            em.persist(new Employee("Chris", "knoesen",50000));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

   /* @Test
    public void testGettingAnEmployeeById() {
        String actual = facade.getById(1).getName();
        String expected = "Frank";
        assertEquals(expected,actual);
    } */

    @Test
    public void testGettingAnEmployeeByName() {
        Employee employeeActual = facade.getEmployeeByName("Frank");
        String nameActual = employeeActual.getName();
        String expected = "Frank";
        assertEquals(expected, nameActual);
    }

    @Test
    public void testGettingAllEmployees() {
        int actual = facade.getAll().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    public void testGettingEmployeesWithHighestSalary() {
        String actual = facade.getEmployeesWithHighestSalary().get(0).getName();
        String expected = "Chris";
        assertEquals(expected, actual);
    }

   /* @Test
    public void testCreatingAnEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO("Henning","Ørestad",1000);
        facade.create(employeeDTO);
        String actual = facade.getById(3).getName();
        String expected = "Henning";
        assertEquals(expected,actual);
    } */

}
