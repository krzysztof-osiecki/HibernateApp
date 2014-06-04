package javahive.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javahive.infrastruktura.StudentRepo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
//import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class StudentTest {
    public static final int LICZBA_STUDENTOW_W_YAML = 12;
    public static final String NAZWISKO = "Nowak";
    public static final String FRAGMENT_NAZWISKA = "a";
    public static final int MIN_ID_VAL = 3;
    public static final int LICZBA_STUDENTOW_PO_DODANIU_JEDNEGO = 13;
    
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    StudentRepo finder;
    @Inject
    RepozytoriumStudent repozytoriumStudentImpl;
        
    @Test
    public void powinienZwrocic11Studentow() {
        //given
        	List<Student> listaStudentow = finder.findAll(Student.class);
        //when
        	for(Student s : listaStudentow){
        	    System.out.println("*****"+s.getNazwisko());
        	}
        	int liczbaStudentow = listaStudentow.size();
        //then
        	assertThat(liczbaStudentow, is(LICZBA_STUDENTOW_W_YAML));
    }
    
    @Test
    public void powinienDodacStudenta() {
        //given
            Student s = new Student();
            s.setImie("Kamil");
            s.setNazwisko("Kwas");
            s.setWieczny(true);
            entityManager.persist(s);
        //when    
            List<Student> listaStudentow = finder.findAll(Student.class);
            int liczbaStudentow = listaStudentow.size();
        //then
	        assertThat(liczbaStudentow, is(LICZBA_STUDENTOW_PO_DODANIU_JEDNEGO));
    }

    @Test
    public void sprawdzLiczbeOcen() {
        //given
        	List<Ocena> oc = finder.findAll(Ocena.class);
        //when
        	int rozmiarListyOcen = oc.size();
        //then
        	assertThat(rozmiarListyOcen, Matchers.greaterThan(0));
    }
    
    //Testy porównujące JPQL/HQL/CRITERIA
    @Test
    public void sprawdzLiczStudPoNazwiskuJPQLvsHQL(){
    	//given
	    	List<Student> listaStudentowHQL  = repozytoriumStudentImpl.getStudenciPoNazwisku_HQL(NAZWISKO);
	    	List<Student> listaStudentowJPQL = repozytoriumStudentImpl.getStudenciPoNazwisku_JPQL(NAZWISKO);
	   	//when
	    	int iloscStudHQL = listaStudentowHQL.size();
	    	int iloscStudJPQL= listaStudentowJPQL.size();
    	//then
	    	assertThat(iloscStudHQL, Matchers.is(iloscStudJPQL));
    }
    
    //ZADANIE - wypełnić
    @Test
    public void sprawdzLiczbeStudentowPoNazwiskuJPQLvsCRITERIA(){
        //given
            List<Student> listaStudentowCriteria  = repozytoriumStudentImpl.getStudenciPoNazwisku_CRITERIA(NAZWISKO);
            List<Student> listaStudentowJPQL = repozytoriumStudentImpl.getStudenciPoNazwisku_JPQL(NAZWISKO);
        //when
            int iloscStudCriteria = listaStudentowCriteria.size();
            int iloscStudJPQL= listaStudentowJPQL.size();
        //then
            assertThat(iloscStudCriteria, Matchers.is(iloscStudJPQL));
    	
    }   
    
    @Test
    public void sprawdzLiczbeStudentowPoNazwiskuCRITERIAvsHQL(){
    	//given
            List<Student> listaStudentowCriteria  = repozytoriumStudentImpl.getStudenciPoNazwisku_CRITERIA(NAZWISKO);
            List<Student> listaStudentowHQL  = repozytoriumStudentImpl.getStudenciPoNazwisku_HQL(NAZWISKO);
    	//when
            int iloscStudCriteria = listaStudentowCriteria.size();
            int iloscStudHQL = listaStudentowHQL.size();
    	//then    	
            assertThat(iloscStudCriteria,Matchers.is(iloscStudHQL));
    }
    
    //Testy na użycie filtrów Hibernate  
    @Test
    public void sprawdzLiczbeStudentowZWiekszymIDNizZadane(){
    	//given
    		List<Student> listaStudZIDPowyzejMin =
    				repozytoriumStudentImpl.getStudenciZIDWiekszymNizDolnaWartosc(MIN_ID_VAL);
    	//when
    		int ile = listaStudZIDPowyzejMin.size();
    		
    	//then
    		System.out.println("ok:"+listaStudZIDPowyzejMin.size());
    		assertThat(ile, Matchers.is(9));		
    }
    
    @Test
    public void powinienZwrocicStudentowZNazwiskiemZawierajcymFragment(){
    	//given
    		List<Student> listaStudZFiltremNaNazwisko =
    				repozytoriumStudentImpl.getStudenciZFiltorwanymNazwiskiem(FRAGMENT_NAZWISKA);
    		List<Student> listaStudentowJPQLZFragmentemNazwiska  = 
    				repozytoriumStudentImpl.getStudenciJPQLPoFragmencieNazwiska(FRAGMENT_NAZWISKA);
    		
    	//when
    		int iloscStudOdfiltrowanych = listaStudZFiltremNaNazwisko.size();
    		int iloscStudZFragmNazwiskaJPQL = listaStudentowJPQLZFragmentemNazwiska.size();
    		
    	//then		    		
    		assertThat(iloscStudOdfiltrowanych, Matchers.is(iloscStudZFragmNazwiskaJPQL));
    }
    
    // Test na użycie INTERFACEu projekcji z pakietu criteria
    @Test
    public void powinienProjekcjaZliczycStudentow(){
    	//given
    	    List<Student> studenci = repozytoriumStudentImpl.getProjekcjaStudentowPoImieNazwisko();
    	//when
    	    int liczbaStudentow = studenci.size();
    	//then     
    	    assertThat(liczbaStudentow, Matchers.is(LICZBA_STUDENTOW_W_YAML));
    }
    
    @Test
    public void powinienZwrocicProjekcjaStudentowRosnacoPoNumerzeIndeksu(){
    	//given
    	    List<Student> studenci = repozytoriumStudentImpl.getStudenciPoIndeksieRosnaco();
    	    int studentsInOrder = 1;
    	//when
    	    Student studentPrev = null;
    	    for(Student s : studenci){
    	        if(studentPrev == null){
    	            studentPrev = s;
    	            continue;
    	        }
    	        
    	        int numerIndeksuPrev = Integer.parseInt(studentPrev.getIndeks().getNumer());
    	        int numerIndeksuNext = Integer.parseInt(s.getIndeks().getNumer());
    	        
    	        if(numerIndeksuPrev < numerIndeksuNext){
    	            studentsInOrder++;
    	        }
    	        
    	        studentPrev = s;
    	    }
    	//then
    	    assertThat(studentsInOrder, Matchers.is(LICZBA_STUDENTOW_W_YAML));
    }
    
    //skopany test, trzeba dobrze powiazać oceny
    @Test
    public void sprawdzOceny() {
        //Student s = finder.findAll(Student.class).get(0);
        
        //List<Student> studenci = repozytoriumStudentImpl.getStudenciPoNazwisku_JPQL("Abramek");
        
        //Student s = studenci.get(0);
        //System.out.println("***" + s.getImie());
        //assertThat(s.getIndeks().getZaliczenia().size(), is(4));
        //System.out.println(s.getOceny());
        //given
            List<Zaliczenie> zaliczeniaStudenta = repozytoriumStudentImpl.getZaliczeniaDlaStudentaPoNazwisku_JPQL("Abramek");
        //when
            int liczbaZaliczen = zaliczeniaStudenta.size();
        //then
            assertThat(liczbaZaliczen, is(4));
    }
   
    
    @Test
    public void powinienZwrocicWieleIndeksow() {
        List<Indeks> indeksy = finder.findAll(Indeks.class);
        System.out.println(indeksy.size());
    }
    

}
