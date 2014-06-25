package javahive.infrastruktura;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fixy.Fixy;
import com.fixy.JpaFixyBuilder;

import javahive.domain.Indeks;
import javahive.domain.Ocena;
import javahive.domain.Przedmiot;
import javahive.domain.Student;
import javahive.domain.Wyklad;
import javahive.domain.Wykladowca;
import javahive.domain.Zaliczenie;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Component
public class DBFiller implements ApplicationContextAware{

	@Inject
    private StudentRepo finder;

    @Inject
    private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void fill(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Fixy fixtures = new JpaFixyBuilder(entityManager).withDefaultPackage("h2_TestData").useFieldAccess().build();
		       
		addOceny(entityManager);		
		addWykladowcy(entityManager);        
        addPrzedmioty(entityManager);        
        addWyklady(entityManager);
        addIndeksy(entityManager);
        addStudenty(entityManager);
        addZaliczenia(entityManager);
        
		transaction.commit();
		entityManager.close();
	}
    private void addStudenty(EntityManager entityManager) {
        boolean wieczny = true;
        boolean niewieczny = false;
        
        addStudentForTest(entityManager, niewieczny, "Adam", "Abramek" , 1);
        addStudentForTest(entityManager, niewieczny, "Adam", "Abramek1", 2);
        addStudentForTest(entityManager, wieczny, "Bartek", "Bielecki" , 10);
        addStudentForTest(entityManager, niewieczny, "Cezary", "Cap", 3);
        addStudentForTest(entityManager, niewieczny, "Dariusz", "Dziewulski" , 9);
        addStudentForTest(entityManager, wieczny, "Edward","Eklerek" , 4);
        addStudentForTest(entityManager, wieczny, "Filip","Fiflak" , 11);
        addStudentForTest(entityManager, niewieczny, "Grzegorz", "Grabowski" , 8);
        addStudentForTest(entityManager, niewieczny, "Henryk", "Heller" , 5);
        addStudentForTest(entityManager, niewieczny, "Ireneusz", "Ikarus" ,7);
        addStudentForTest(entityManager, wieczny, "Jędrzej", "Jaworowski", 6);
        addStudentForTest(entityManager, niewieczny, "Natan", "Nowak" ,12);
    }
    private void addStudentForTest(EntityManager entityManager,
            boolean wieczny, String imie, String nazwisko, int indeksId) {
        Student s1 = new Student();
        s1.setImie(imie);
        s1.setNazwisko(nazwisko);
        s1.setIndeks(entityManager.find(Indeks.class, indeksId));
        s1.setWieczny(wieczny);
        entityManager.persist(s1);
    }
    private void addIndeksy(EntityManager entityManager) {
        addIndeksForTest(entityManager, "000001");
        addIndeksForTest(entityManager, "000002");
        addIndeksForTest(entityManager, "000003");
        addIndeksForTest(entityManager, "000004");
        addIndeksForTest(entityManager, "000005");
        addIndeksForTest(entityManager, "000006");
        addIndeksForTest(entityManager, "000007");
        addIndeksForTest(entityManager, "000008");
        addIndeksForTest(entityManager, "000009");
        addIndeksForTest(entityManager, "000010");
        addIndeksForTest(entityManager, "000011");
        addIndeksForTest(entityManager, "000012");
    }
    private void addIndeksForTest(EntityManager entityManager, String numer) {
        Indeks i1 = new Indeks();
        i1.setNumer(numer);
        entityManager.persist(i1);
    }
    private void addOceny(EntityManager entityManager) {
        addOcenaForTest(entityManager, "2.0");
        addOcenaForTest(entityManager, "2.5");
        addOcenaForTest(entityManager, "3.0");
        addOcenaForTest(entityManager, "3.5");
        addOcenaForTest(entityManager, "4.0");
        addOcenaForTest(entityManager, "4.5");
        addOcenaForTest(entityManager, "5.0");
    }
    private void addWyklady(EntityManager entityManager) {
        addWykladForTest(entityManager, 1, 1); // ASD_KRZ
        addWykladForTest(entityManager, 2, 2); // JAV_KUD
        addWykladForTest(entityManager, 3, 3); // PRO_MIK
        addWykladForTest(entityManager, 4, 3); // GRA_MIK
        addWykladForTest(entityManager, 5, 4); // ANA_KAC
        addWykladForTest(entityManager, 5, 5); // ANA_GOE
    }
    private void addZaliczenia(EntityManager entityManager) {
        addZaliczenieForTest(entityManager,1 ,1 ,1 );
        addZaliczenieForTest(entityManager,2 ,2 ,1 );
        addZaliczenieForTest(entityManager,3 ,3 ,1 );
        addZaliczenieForTest(entityManager,4 ,4 ,1 );
        addZaliczenieForTest(entityManager,5 ,5 ,1 );
        addZaliczenieForTest(entityManager,6 ,6 ,1 );
        addZaliczenieForTest(entityManager,7 ,7 ,1 );
        addZaliczenieForTest(entityManager,8 ,1 ,1 );
        addZaliczenieForTest(entityManager,9 ,2 ,1 );
        addZaliczenieForTest(entityManager,10 ,3 ,1 );
        addZaliczenieForTest(entityManager,11 ,4 ,1 );
        addZaliczenieForTest(entityManager,12 ,5 ,1 );
        addZaliczenieForTest(entityManager,1 ,6 ,2 );
        addZaliczenieForTest(entityManager,3 ,7 ,2 );
        addZaliczenieForTest(entityManager,5 ,1 ,2 );
        addZaliczenieForTest(entityManager,7 ,2 ,2 );
        addZaliczenieForTest(entityManager,9 ,3 ,2 );
        addZaliczenieForTest(entityManager,11 ,4 ,2 );
        addZaliczenieForTest(entityManager,2 ,5 ,3 );
        addZaliczenieForTest(entityManager,4 ,6 ,3 );
        addZaliczenieForTest(entityManager,6 ,7 ,3 );
        addZaliczenieForTest(entityManager,8 ,1 ,3 );
        addZaliczenieForTest(entityManager,10 ,2 ,3 );
        addZaliczenieForTest(entityManager,12 ,3 ,3 );
        addZaliczenieForTest(entityManager,1 ,4 ,4 );
        addZaliczenieForTest(entityManager,2 ,5 ,4 );
        addZaliczenieForTest(entityManager,3 ,6 ,4 );
        addZaliczenieForTest(entityManager,4 ,7 ,4 );
        addZaliczenieForTest(entityManager,5 ,1 ,4 );
        addZaliczenieForTest(entityManager,6 ,2 ,4 );
        addZaliczenieForTest(entityManager,7 ,3 ,4 );
        addZaliczenieForTest(entityManager,8 ,4 ,4 );
        addZaliczenieForTest(entityManager,9 ,5 ,4 );
        addZaliczenieForTest(entityManager,10 ,6 ,4 );
        addZaliczenieForTest(entityManager,11 ,7 ,4 );
        addZaliczenieForTest(entityManager,12 ,1 ,4 );
        addZaliczenieForTest(entityManager,1 ,2 ,5 );
        addZaliczenieForTest(entityManager,2 ,3 ,5);
        addZaliczenieForTest(entityManager,3 ,4 ,5 );
        addZaliczenieForTest(entityManager,4 ,5 ,5 );
        addZaliczenieForTest(entityManager,5 ,6 ,5 );
        addZaliczenieForTest(entityManager,6 ,7 ,5 );
        addZaliczenieForTest(entityManager,7 ,1 ,6 );
        addZaliczenieForTest(entityManager,8 ,2 ,6 );
        addZaliczenieForTest(entityManager,9 ,3 ,6 );
        addZaliczenieForTest(entityManager,10 ,4 ,6 );
        addZaliczenieForTest(entityManager,11 ,5 ,6 );
        addZaliczenieForTest(entityManager,12 ,6 ,6 );
    }
    private void addZaliczenieForTest(EntityManager entityManager,
            int indeksId, int ocenaId, int wykladId) {
        Zaliczenie z1 = new Zaliczenie();
        z1.setIndeks(entityManager.find(Indeks.class, indeksId));
        z1.setOcena(entityManager.find(Ocena.class, ocenaId));
        z1.setWyklad(entityManager.find(Wyklad.class, wykladId));
        entityManager.persist(z1);
    }
    private void addPrzedmioty(EntityManager entityManager) {
        addPrzedmiotForTest(entityManager, "Algorytmy i struktury danych");
        addPrzedmiotForTest(entityManager, "Java - programowanie obiektowe");
        addPrzedmiotForTest(entityManager, "Podstawy programowania");
        addPrzedmiotForTest(entityManager, "Grafika komputerowa");
        addPrzedmiotForTest(entityManager, "Analiza matematyczna");
    }
    private void addWykladowcy(EntityManager entityManager) {
        addWykladowcaForTest(entityManager, "dr","Jacek","Krzaczkowski");
        addWykladowcaForTest(entityManager, "dr","Rajmund","Kuduk");
        addWykladowcaForTest(entityManager, "prof. dr hab.","Paweł","Mikołajczak");
        addWykladowcaForTest(entityManager, "dr hab.","Wiesława","Kaczor");
        addWykladowcaForTest(entityManager, "prof. dr hab.","Kazimierz","Goebel");
    }
    private void addWykladForTest(EntityManager entityManager, int przedmiotId,
            int wykladowcaId) {
        Wyklad w1 = new Wyklad();
        w1.setPrzedmiot(entityManager.find(Przedmiot.class, przedmiotId));
        w1.setWykladowca(entityManager.find(Wykladowca.class, wykladowcaId));
        entityManager.persist(w1);
    }
    private void addPrzedmiotForTest(EntityManager entityManager, String nazwa) {
        Przedmiot p1 = new Przedmiot();
        p1.setNazwa(nazwa);
        entityManager.persist(p1);
    }
    private void addOcenaForTest(EntityManager entityManager, String wysokosc) {
        Ocena o1 = new Ocena();
		o1.setWysokosc(wysokosc);
		entityManager.persist(o1);
    }
    private void addWykladowcaForTest(EntityManager entityManager,
            String stopien, String imie, String nazwisko) {
        Wykladowca w1 = new Wykladowca();
        w1.setStopien(stopien); 
		w1.setImie(imie);
		w1.setNazwisko(nazwisko);	
		entityManager.persist(w1);
    }



	private ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
}
