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
		String queryString = "SELECT s FROM Student s WHERE s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("name", name); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsWithLastName(String lastName) {
		String queryString = "SELECT s FROM Student s WHERE s.nazwisko = :lastName";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("lastName", lastName); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsWithFullName(String name, String lastName) {
		String queryString = "SELECT s FROM Student s WHERE s.nazwisko = :lastName AND s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("lastName", lastName); 
		query.setParameter("name", name);
		return ((List<Student>) query.getResultList());
	}
	
	public Student findStudentWithIndexNumber(String indexNumber) {
		String queryString = "SELECT s FROM Student s WHERE s.index_id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", indexNumber);
		return (Student) query.getResultList();
	}
	
	public boolean deleteStudentWithIndexNumber(String indexNumber) {
		String queryString = "DELETE FROM Student s WHERE s.indeks_id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", indexNumber);
		return true;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Zaliczenie> findCreditsOfStudent(int id) {
		String queryString = "SELECT * FROM Zaliczenie WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", String.valueOf(id)); 
		return ((List<Zaliczenie>) query.getResultList());
	}*/
	
	@SuppressWarnings("unchecked")
	public List<Zaliczenie> findCreditsOfStudent(int id) {
	    String queryString2 = "SELECT z FROM Zaliczenie z INNER JOIN z.indeks i INNER JOIN i.student s " +
                "WHERE s.id = :id";
        query = entityManager.createQuery(queryString2);
        query.setParameter("id", id);
        return (List<Zaliczenie>)query.getResultList();
    }
	
}
