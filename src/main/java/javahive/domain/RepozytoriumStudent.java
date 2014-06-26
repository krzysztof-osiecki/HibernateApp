package javahive.domain;

import java.util.List;


public interface RepozytoriumStudent {
	List<Student> getStudenciPoNazwiskuHQL(String nazwisko);
	List<Student> getStudenciPoNazwiskuJPQL(String nazwisko);
	List<Student> getStudenciPoNazwiskuCRITERIA(String nazwisko);
	List<Zaliczenie> getZaliczeniaDlaStudentaPoNazwiskuJPQL(String nazwisko);
    
	//Filtry - z Hibernate
	List<Student> getStudenciZFiltorwanymNazwiskiem(String fragmentNazwiska);
	List<Student> getStudenciJPQLPoFragmencieNazwiska(String fragmentNazwiska);
	
	List<Student> getStudenciZIDWiekszymNizDolnaWartosc(int minID);
	
	//Projekcje
	List<Student> getProjekcjaStudentowPoImieNazwisko();
    List<Student> getStudenciPoNazwiskuZaczynajacymSieOdLiter(String nazwisko);
    List<Student> getStudenciPoIndeksieRosnaco();
}
