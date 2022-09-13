package facades;

import dtos.PersonDTO;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

//import errorhandling.PersonNotFoundException;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO create(PersonDTO personDTO){
        Person personEntity = new Person(personDTO.getName(), personDTO.getAge());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(personEntity);
    }

    public PersonDTO getById(long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null)
            throw new PersonNotFoundException("The Person entity with ID: " + id + " Was not found");
        return new PersonDTO(person);
    }

    
    public List<PersonDTO> getAll() throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        if(query == null)
            throw new PersonNotFoundException("No persons were found");
        List<Person> persons = query.getResultList();
        return PersonDTO.getDtos(persons);
    }

    public PersonDTO updatePerson(PersonDTO personDTO){
        EntityManager em = getEntityManager();

        Person fromDB = em.find(Person.class,personDTO.getId());

        if(fromDB == null) {
            throw new EntityNotFoundException("No such Person with id: " + personDTO.getId());
        }
        Person personEntity = new Person(personDTO.getId(),personDTO.getName(), personDTO.getAge());

            try {
                em.getTransaction().begin();
                em.merge(personEntity);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(personEntity);

    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
       // fe.getAll().forEach(dto->System.out.println(dto));
        fe.updatePerson(new PersonDTO(2,"huehuehue",13));
    }

}
