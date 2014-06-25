package javahive.infrastruktura;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fixy.Fixy;
import com.fixy.JpaFixyBuilder;

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
		String stopien,imie,nazwisko;
        /*addWykladowcaForTest(entityManager, "dr","Jacek","Krzaczkowski");
        addWykladowcaForTest(entityManager, "prof. dr hab.","Pawel","Krzaczkowski");
        addWykladowcaForTest(entityManager, "dr","Jacek","Krzaczkowski");
        addWykladowcaForTest(entityManager, "dr","Jacek","Krzaczkowski");
        addWykladowcaForTest(entityManager, "dr","Jacek","Krzaczkowski");
		    
		- Wykladowca(MIK):
		    stopien: "prof. dr hab"
		    imie: Paweł
		    nazwisko: Mikołajczak
		    
		- Wykladowca(KAC):
		    stopien: "dr hab."
		    imie: Wiesława
		    nazwisko: Kaczor
		    
		- Wykladowca(GOE):
		    stopien: "prof. dr hab."
		    imie: Kazimierz
		    nazwisko: Goebel
		*/
		fixtures.load("h2_TestData/Studenci.yaml");
		fixtures.load("h2_TestData/Wykladowcy.yaml");
		fixtures.load("h2_TestData/Przedmioty.yaml");
		fixtures.load("h2_TestData/Oceny.yaml");
        fixtures.load("h2_TestData/Indeksy.yaml");
		transaction.commit();
		entityManager.close();
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
