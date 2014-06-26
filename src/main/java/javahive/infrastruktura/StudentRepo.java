package javahive.infrastruktura;

import java.util.ArrayList;
import java.util.List;

import javahive.domain.Student; //NOSONAR
import javahive.domain.Zaliczenie; //NOSONAR
import javahive.domain.Wyklad; //NOSONAR
import javahive.domain.Indeks; //NOSONAR
import javahive.domain.Ocena; //NOSONAR

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

@Component
public class StudentRepo{
	
	@PersistenceContext
	private EntityManager entityManager;
	private Query query;
	private static final String NAME = "name";
	private static final String LAST_NAME = "lastName";
	private static final int DLUGOSC_INDEXU = 6;
	
	public StudentRepo(){}
	
	public <T> List<T> findAll(Class <T> c) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(c);
		Root<T> entityRoot = criteria.from(c);
		criteria.select(entityRoot);
		return entityManager.createQuery( criteria ).getResultList();
	}
	
	@SuppressWarnings("unchecked") //NOSONAR
	public List<Student> findStudentsWithName(String name) {
		String queryString = "SELECT s FROM Student s WHERE s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter(NAME, name); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked") //NOSONAR
	public List<Student> findStudentsWithLastName(String lastName) {
		String queryString = "SELECT s FROM Student s WHERE s.nazwisko = :lastName";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter(LAST_NAME, lastName); 
		return ((List<Student>) query.getResultList());
	}
	
	@SuppressWarnings("unchecked") //NOSONAR
	public List<Student> findStudentsWithFullName(String name, String lastName) {
		String queryString = "SELECT s FROM Student s WHERE s.nazwisko = :lastName AND s.imie = :name";
		query = (Query) entityManager.createQuery(queryString);
		query.setParameter(LAST_NAME, lastName); 
		query.setParameter(NAME, name);
		return ((List<Student>) query.getResultList());
	}
	
	public List<Student> findStudentsBy(String indexNumber, String name, String lastName){
	    String queryString = "SELECT s FROM Student s WHERE s.nazwisko LIKE :lastName OR s.imie LIKE :name OR s.indeks.numer LIKE :indexNumber";
	    
        query = (Query) entityManager.createQuery(queryString);
        query.setParameter(LAST_NAME, "%"+lastName+"%"); 
        query.setParameter(NAME, "%"+name+"%");
        query.setParameter("indexNumber", "%"+indexNumber+"%");
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
		query.setParameter(NAME, name);
		query.setParameter(LAST_NAME, lastName);
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
	
	
	@SuppressWarnings("unchecked") //NOSONAR
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
	    if(result.length()>DLUGOSC_INDEXU){
	        return "000000";
	    }
	    else if(result.length()<DLUGOSC_INDEXU){
	        for(int i = result.length(); i < DLUGOSC_INDEXU; i++){
	            result = "0"+result;
	        }
	        return result;
	    }
	    return result;
	}
	
    public boolean utworzStudenta(String imie, String nazwisko, int[] wykladIds) 
    throws IllegalArgumentException{ //NOSONAR
    	List<Student> lista = findStudentsWithFullName(imie, nazwisko);
    	if(lista.size() != 0 ) 
    	{
    		throw new IllegalArgumentException("Student juz istnieje w bazie");
    	}
        List<Zaliczenie> zaliczenia = new ArrayList<Zaliczenie>();
        for(int idWykladu: wykladIds){
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
            throw new IllegalArgumentException("Zabraklo numerow idexu");
        }
        
        entityManager.persist(indeks);         
        entityManager.persist(student);
        for(Zaliczenie z:zaliczenia){
            entityManager.persist(z);
        }
        
        return true;
    }
	
}
