package javahive.infrastruktura;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fixy.Fixy;
import com.fixy.JpaFixyBuilder;

import javahive.domain.Ocena;
import javahive.domain.Przedmiot;
import javahive.domain.Wyklad;
import javahive.domain.Wykladowca;

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
		
       
		addOcenaForTest(entityManager, "2.0");
        addOcenaForTest(entityManager, "2.5");
        addOcenaForTest(entityManager, "3.0");
        addOcenaForTest(entityManager, "3.5");
        addOcenaForTest(entityManager, "4.0");
        addOcenaForTest(entityManager, "4.5");
        addOcenaForTest(entityManager, "5.0");
		
		addWykladowcy(entityManager);        
        addPrzedmioty(entityManager);        
        
        addWykladForTest(entityManager, 1, 1); // ASD_KRZ
        addWykladForTest(entityManager, 2, 2); // JAV_KUD
        addWykladForTest(entityManager, 3, 3); // PRO_MIK
        addWykladForTest(entityManager, 4, 3); // GRA_MIK
        addWykladForTest(entityManager, 5, 4); // ANA_KAC
        addWykladForTest(entityManager, 5, 5); // ANA_GOE
        
        /*fixtures.load("h2_TestData/Studenci.yaml");
		fixtures.load("h2_TestData/Wykladowcy.yaml");
		fixtures.load("h2_TestData/Przedmioty.yaml");
		fixtures.load("h2_TestData/Oceny.yaml");
        fixtures.load("h2_TestData/Indeksy.yaml");*/
		transaction.commit();
		entityManager.close();
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
