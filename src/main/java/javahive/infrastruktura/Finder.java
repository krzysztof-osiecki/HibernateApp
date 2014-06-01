package javahive.infrastruktura;

import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.api.dto.WykladDTO;
import javahive.domain.Wyklad;
import javahive.domain.Student;
import javahive.domain.Zaliczenie;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

@Component
public class Finder{
	
	@PersistenceContext
	public EntityManager entityManager;
	private Query query;
	
	public Finder(){}
	
	public <T> List<T> findAll(Class <T> c) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(c);
		Root<T> entityRoot = criteria.from(c);
		criteria.select(entityRoot);
		List<T> entities = entityManager.createQuery( criteria ).getResultList();
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsWithName(String name) {
		String queryString = "SELECT * FROM Student WHERE s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("name", name); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsWithLastName(String lastName) {
		String queryString = "SELECT * FROM Student WHERE s.nazwisko = :lastName";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("lastName", lastName); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsWithFullName(String name, String lastName) {
		String queryString = "SELECT * FROM Student WHERE s.nazwisko = :lastName AND s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("lastName", lastName); 
		query.setParameter("name", name);
		return ((List<Student>) query.getResultList());
	}
	
	public Student findStudentWithID(int id) {
		String queryString = "SELECT * FROM Student WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", String.valueOf(id));
		return (Student) query.getResultList();
	}
	
	public boolean deleteStudentWithID(int id) {
		String queryString = "DELETE FROM Student WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", String.valueOf(id));
		return true;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Zaliczenie> findCreditsOfStudent(int id) {
		String queryString = "SELECT * FROM Zaliczenie WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", String.valueOf(id)); 
		return ((List<Zaliczenie>) query.getResultList());
	}*/
	
	public List<Zaliczenie> findCreditsOfStudent(int id) {
        //String queryString = "SELECT * FROM Zaliczenie WHERE s.id = :id";     
        //query = (Query) entityManager.createQuery(queryString);
        //query.setParameter("id", String.valueOf(id)); 
        //return ((List<Zaliczenie>) query.list());
        //Session session=entityManager.unwrap(Session.class);
        String queryString = "SELECT z FROM Zaliczenie z INNER JOIN z.indeks i INNER JOIN i.student s " +
                "WHERE LOWER(s.nazwisko) = :nazwisko";
        
        javax.persistence.Query query = entityManager.createQuery(queryString);
        System.out.println("wybor: "+id);
        //query.setParameter("id", id);
        String nazwisko = "Nowak";
        query.setParameter("nazwisko", nazwisko.toLowerCase());
        
        List<Zaliczenie> t = query.getResultList();
        for(Zaliczenie z:t){
            System.out.println(z.getWyklad().getPrzedmiot().getNazwa());
        }
        
        return (List<Zaliczenie>)query.getResultList();
        //return castList(Zaliczenie.class, query.getResultList());
    }
	
}
