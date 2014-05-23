package javahive.infrastruktura;

import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component
public class Finder{
	
	@PersistenceContext
	public EntityManager entityManager;
	private Query query;
	
	public Finder(){}
	
	public <T> List<T> findAll(Class <T> c){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(c);
		Root<T> entityRoot = criteria.from(c);
		criteria.select(entityRoot);
		List<T> entities = entityManager.createQuery( criteria ).getResultList();
		return entities;
	}
	
	public Student findStudentWithID(int id)
	{
		String queryString = "SELECT * FROM Student WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", String.valueOf(id));
		return (Student) query.list();
	}
	
	
}
