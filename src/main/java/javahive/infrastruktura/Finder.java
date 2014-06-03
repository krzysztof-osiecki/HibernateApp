package javahive.infrastruktura;

import java.util.ArrayList;
import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.api.dto.WykladDTO;
import javahive.domain.Indeks;
import javahive.domain.Ocena;
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
	
	public boolean deleteStudentWithIndexNumber(int id) {
		String queryString = "DELETE FROM Student s WHERE s.id = :id";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	
	public boolean setPersonalData(int id, String name, String lastName)
	{
		String queryString = "UPDATE Student s SET s.imie = :name, s.nazwisko = :lastName"
				+ " WHERE s.id = :id";
		query = entityManager.createQuery(queryString);
		query.setParameter("name", name);
		query.setParameter("lastName", lastName);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	
	public boolean setCreditGrade(int idZaliczenia, int ocenaId)
	{
	    String queryString = "UPDATE Zaliczenie z SET z.ocena = :ocena"
                + " WHERE z.id = :idZaliczenia";
        query = entityManager.createQuery(queryString);
        query.setParameter("ocena", entityManager.find(Ocena.class, ocenaId));
        query.setParameter("idZaliczenia", idZaliczenia);
        query.executeUpdate();
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Zaliczenie> findCreditsOfStudent(int id) {
	    String queryString = "SELECT z FROM Zaliczenie z INNER JOIN z.indeks i INNER JOIN i.student s " +
                "WHERE s.id = :id";
        query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        return (List<Zaliczenie>)query.getResultList();
    }
	

	public String zwiekszIndex(String index)
	{
	    int indexInt = Integer.parseInt(index);
	    indexInt++;
	    String result = String.valueOf(indexInt);
	    if(result.length()>6){
	        return "000000";
	    }
	    else if(result.length()<6){
	        for(int i = result.length(); i < 6; i++){
	            result = "0"+result;
	        }
	        return result;
	    }
	    return result;
	    
		/*String result;
		for(int i=index.length()-1; i>0; i++)
		{
			if(index.charAt(i)<'9'){
				result=index.substring(0, i)+(index.charAt(i)+1)+index.substring(i+1, index.length());
				return result;
			}
			else
			{
				index=index.substring(0, i)+'0'+index.substring(i, index.length());
			}
		}
		return null;*/
	}
	
    public boolean utworzStudenta(String imie, String nazwisko, String wykladIds){
        
        String[] wykladIdArray = wykladIds.split(",");
        
        List<Zaliczenie> zaliczenia = new ArrayList<Zaliczenie>();
        for(String idString: wykladIdArray){
            int idWykladu = Integer.parseInt(idString);
            Zaliczenie zaliczenie = new Zaliczenie();
            zaliczenie.setWyklad(
                        entityManager.find(Wyklad.class, idWykladu)
                    );
            zaliczenia.add(zaliczenie);
        }
        
        
        Indeks indeks= new Indeks();        
        indeks.setZaliczenia(zaliczenia);
        
        for(Zaliczenie z:zaliczenia){
            z.setIndeks(indeks);
        }
        
        Student student = new Student.StudentBuilder()
        .imie(imie)
        .nazwisko(nazwisko)
        .indeks(indeks)
        .buduj();
        
        indeks.setStudent(student);
        String queryString = "SELECT MAX(i.numer) FROM Indeks i";
        String maxIndex = (String)entityManager.createQuery(queryString).getSingleResult();
        String nowyIndex = zwiekszIndex(maxIndex);
        if(nowyIndex!=null)
        {
        	indeks.setNumer(nowyIndex);
        }
        else
        {
            indeks.setNumer("000000");
        	//ZABRAKLO INDEXOW, NIE WIEM JAK CHCESZ TO ROZWIAZAC
            //Na zaliczenie nie zabraknie ;p
        }
        
        entityManager.persist(indeks);         
        entityManager.persist(student);
        for(Zaliczenie z:zaliczenia){
            entityManager.persist(z);
        }
        
        return true;
    }
	
}
