package javahive.api;

import java.util.ArrayList;
import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.api.dto.StudentDTOMementoCaretaker;
import javahive.api.dto.WykladDTO;
import javahive.api.dto.ZaliczenieDTO;
import javahive.domain.Student;
import javahive.domain.Wyklad;
import javahive.domain.Zaliczenie;
import javahive.infrastruktura.Finder;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by m on 29.04.14.
 */

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class StudenciApi {
    @Inject
    private Finder finder;
    private StudentDTOMementoCaretaker mementoCaretaker;
    public List<StudentDTO> getListaWszystkichStudentow() {
        List studentciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findAll(Student.class)) {
            StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
            .imie(student.getImie())
            .nazwisko(student.getNazwisko())
            .wieczny(student.isWieczny())
            .indeks(student.getIndeks())
            .id(student.getId())
            .buduj();
            mementoCaretaker.setMemento(studentDTO.createMemento());
            studentciDTO.add(studentDTO);
        }
        return studentciDTO;
    }
    
    //Dodatkowa funkcjonalnosc
    public List<WykladDTO> getListaWszystkichWykladow() {
        List wykladyDTO=new ArrayList<WykladDTO>();
        for(Wyklad wyklad: finder.findAll(Wyklad.class)) {
            WykladDTO wykladDTO= new WykladDTO.WykladDTOBuilder()
            .wykladowca(wyklad.getWykladowca())
            .przedmiot(wyklad.getPrzedmiot())
            .build();
            wykladyDTO.add(wykladDTO);
        }
        return wykladyDTO;
    }


    public int getLiczbaStudentow() {
        return finder.findAll(Student.class).size();
    }
    
    public StudentDTO znajdzStudenta(String indexNumber) {
    	Student student=finder.findStudentWithIndexNumber(indexNumber);
        StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
        .imie(student.getImie())
        .nazwisko(student.getNazwisko())
        .wieczny(student.isWieczny())
        .indeks(student.getIndeks())
        .id(student.getId())
        .buduj();
        mementoCaretaker.setMemento(studentDTO.createMemento());
        return studentDTO;
    }
    
    public List<StudentDTO> znajdzStudentow(String imie, String nazwisko) {
        List studenciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findStudentsWithFullName(imie, nazwisko)) {
        	StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
            .imie(student.getImie())
            .nazwisko(student.getNazwisko())
            .wieczny(student.isWieczny())
            .indeks(student.getIndeks())
            .id(student.getId())
            .buduj();
            studenciDTO.add(studentDTO);
            mementoCaretaker.setMemento(studentDTO.createMemento());
        }
        return studenciDTO;
    }
    
    public List<StudentDTO> znajdzStudentow(String param, int wariant) {
    	List studenciDTO=new ArrayList<StudentDTO>();
    	if(wariant==0)	//param==imie
    	{
            for(Student student: finder.findStudentsWithName(param)) {
            	StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
                .imie(student.getImie())
                .nazwisko(student.getNazwisko())
                .wieczny(student.isWieczny())
                .indeks(student.getIndeks())
                .id(student.getId())
                .buduj();
                mementoCaretaker.setMemento(studentDTO.createMemento());
                studenciDTO.add(studentDTO);
            }
    	}
    	else	//param==nazwisko
    	{
            for(Student student: finder.findStudentsWithLastName(param)) {
            	StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
                .imie(student.getImie())
                .nazwisko(student.getNazwisko())
                .wieczny(student.isWieczny())
                .indeks(student.getIndeks())
                .id(student.getId())
                .buduj();
                mementoCaretaker.setMemento(studentDTO.createMemento());
                studenciDTO.add(studentDTO);
            }
    	}
    	return  studenciDTO;
    }
    
    public boolean usunStudenta(String indexNumber) {
        return finder.deleteStudentWithIndexNumber(indexNumber);
    }
    
    public List<ZaliczenieDTO> pobierzZaliczenia(int studentId) {
        List zaliczeniaDTO=new ArrayList<ZaliczenieDTO>();
        for(Zaliczenie zaliczenie: finder.findCreditsOfStudent(studentId)) {
        	ZaliczenieDTO zaliczenieDTO = new ZaliczenieDTO.ZaliczenieDTOBuilder()
        	.ocena(zaliczenie.getOcena())
        	.wyklad(zaliczenie.getWyklad())
            .buduj();
            zaliczeniaDTO.add(zaliczenieDTO);
        }
        return zaliczeniaDTO;
    }
    
    public boolean wystawOcene(int studentId, int zaliczenieId) {
        //TODO public boolean wystawOcene(int studentId, int zaliczenieId){
        return false;
    }
    
    public StudentDTO przywrocStudenta(String indexNumber) {
        return new StudentDTO(mementoCaretaker.getMementoOfStudent(indexNumber));
    }

}
