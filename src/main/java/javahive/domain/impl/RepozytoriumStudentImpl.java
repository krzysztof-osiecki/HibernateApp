package javahive.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javahive.domain.Indeks;
import javahive.domain.RepozytoriumStudent;
import javahive.domain.Student;
import javahive.domain.Zaliczenie;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;


@Component
public class RepozytoriumStudentImpl implements RepozytoriumStudent {
	private static final String QUERY_STUDENT_LASTNAME = "SELECT s FROM Student s " +
															"WHERE LOWER(s.nazwisko) = :nazwisko";
	private static final String QUERY_STUDENT_LIKE_LASTNAME = "FROM Student s " +
															"WHERE LOWER(s.nazwisko) = :nazwisko";
	//JPQL jest dziwny... BARDZO dziwny
	private static final String QUERY_ZALICZENIA_DLA_STUDENTA_NAZWISKO = "SELECT z FROM Zaliczenie z INNER JOIN z.indeks i INNER JOIN i.student s " +
	                                                                        "WHERE LOWER(s.nazwisko) = :nazwisko"; 
	
	private static final String QUERY_STUDENT = "FROM Student";
	
    @PersistenceContext
    private EntityManager entityManager;

    private static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for(Object o: c)
        {
          r.add(clazz.cast(o));
        }
        return r;
    }
    
    @Override
    public List<Student> getStudenciPoNazwiskuHQL(String nazwisko) {
        Session session=entityManager.unwrap(Session.class);
        org.hibernate.Query query = session.createQuery(QUERY_STUDENT_LASTNAME);
        query.setParameter("nazwisko", nazwisko.toLowerCase());
        
        return castList(Student.class, query.list()); //session close?
    }

	@Override
	public List<Student> getStudenciPoNazwiskuJPQL(String nazwisko) {
		 javax.persistence.Query query = entityManager.createQuery(QUERY_STUDENT_LASTNAME);
		 query.setParameter("nazwisko", nazwisko.toLowerCase()); 
		return castList(Student.class, query.getResultList());
	}
	
	@Override
	public List<Zaliczenie> getZaliczeniaDlaStudentaPoNazwiskuJPQL(String nazwisko){
        javax.persistence.Query query = entityManager.createQuery(this.QUERY_ZALICZENIA_DLA_STUDENTA_NAZWISKO);
        query.setParameter("nazwisko", nazwisko.toLowerCase());
	    return castList(Zaliczenie.class, query.getResultList());
	}

	@Override
	public List<Student> getStudenciPoNazwiskuCRITERIA(String nazwisko) {
		Session session=entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Student.class);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("nazwisko",nazwisko));//.toLowerCase()));
		//criteria.setProjection(Projections.distinct(Projections.property("id")));
		//return session.createCriteria(nu).uniqueResult();
		for(Object o : criteria.list()){
		    System.out.println("##"+((Student)o).getNazwisko());
		}
		
		return castList(Student.class, criteria.list());
	}
	
    @Override
    public List<Student> getStudenciPoNazwiskuZaczynajacymSieOdLiter(String nazwisko) {
        return null;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudenciZFiltorwanymNazwiskiem(String fragmentNazwiska) {
		Session session=entityManager.unwrap(Session.class);
		
		Filter filter = session.enableFilter("FILTER_TEST_STUDENT_NAZWISKO");
		filter.setParameter("PARAM_student_Nazwisko", fragmentNazwiska.toLowerCase());
		
		org.hibernate.Query query = session.createQuery(QUERY_STUDENT);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getStudenciJPQLPoFragmencieNazwiska(String fragmentNazwiska){
		 javax.persistence.Query query = entityManager.createQuery(QUERY_STUDENT_LIKE_LASTNAME);
		 query.setParameter("nazwisko", "%"+fragmentNazwiska.toLowerCase()+"%");	
		 return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudenciZIDWiekszymNizDolnaWartosc(int minID) {
		Session session=entityManager.unwrap(Session.class);
		
		Filter filter = session.enableFilter("FILTER_TEST_STUDENT_ID");
		filter.setParameter("PARAM_student_ID",  minID);
		
		/*Filter filter = session.enableFilter("studentFilter");
    	filter.setParameter("studentFilterID", minID);*/
		
		org.hibernate.Query query = session.createQuery(QUERY_STUDENT);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getProjekcjaStudentowPoImieNazwisko() {
		Session session=entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Student.class);
		
		ProjectionList proList = Projections.projectionList();
		
        proList.add(Projections.property("imie"));
        proList.add(Projections.property("nazwisko"));
        
        //Po kiego grzyba takie projekcje?!
        
        List<Object> listToParse = criteria.setProjection(proList).list();
        List<Student> parsedList = new LinkedList<Student>();
        for(Object o : listToParse){
            Object[] r = (Object[]) o;
            System.out.println("**"+r[0]+" "+r[1]);
            Student s = new Student();
            s.setImie(r[0].toString());
            s.setNazwisko(r[1].toString());
            parsedList.add(s);
        }       
        
		return parsedList;
	}
	

    @Override
    public List<Student> getStudenciPoIndeksieRosnaco() {
        Session session=entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Student.class);
        criteria.createAlias("indeks", "indeksId",JoinType.LEFT_OUTER_JOIN);
        ProjectionList proList = Projections.projectionList();
        
        proList.add(Projections.property("indeksId.numer"),"indeks");
        proList.add(Projections.property("imie"),"imie");
        proList.add(Projections.property("nazwisko"),"nazwisko");
        proList.add(Projections.property("wieczny"),"wieczny");
        
        criteria.addOrder(Order.asc("indeksId.numer"));        
        List<Object> listToParse = criteria.setProjection(proList).list();
        List<Student> parsedList = new LinkedList<Student>();
        
        for(Object o : listToParse){
            Object[] r = (Object[]) o;            
            Indeks i = new Indeks();
            i.setNumer(r[0].toString());
            Student s = new Student();
            s.setIndeks(i);
            s.setImie(r[1].toString());
            s.setNazwisko(r[2].toString());
            if(r[3].toString().equals("true")){
                s.setWieczny(true);
            }
            else {
                s.setWieczny(false);
            }
            parsedList.add(s);
        }
        
        return parsedList;
    }
}
