package javahive.api;

import java.util.ArrayList;
import java.util.List;

import javahive.api.dto.OcenaDTO;
import javahive.api.dto.StudentDTO;
import javahive.api.dto.WykladDTO;
import javahive.api.dto.ZaliczenieDTO;
import javahive.domain.Ocena;
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
            studentciDTO.add(studentDTO);
        }
        return studentciDTO;
    }
    
    //Dodatkowa funkcjonalnosc
    public List<WykladDTO> getListaWszystkichWykladow() {
        List wykladyDTO=new ArrayList<WykladDTO>();
        for(Wyklad wyklad: finder.findAll(Wyklad.class)) {
            WykladDTO wykladDTO= new WykladDTO.WykladDTOBuilder()
            .id(wyklad.getId())
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
                studenciDTO.add(studentDTO);
            }
    	}
    	return  studenciDTO;
    }
    
    public boolean usunStudenta(int id) {
        return finder.deleteStudentWithIndexNumber(id);
    }
    
    public List<ZaliczenieDTO> pobierzZaliczenia(int studentId) {
        List zaliczeniaDTO=new ArrayList<ZaliczenieDTO>();
        for(Zaliczenie zaliczenie: finder.findCreditsOfStudent(studentId)) {
        	ZaliczenieDTO zaliczenieDTO = new ZaliczenieDTO.ZaliczenieDTOBuilder()
        	.id(zaliczenie.getId())
        	.ocena(zaliczenie.getOcena())
        	.wyklad(zaliczenie.getWyklad())
            .buduj();
            zaliczeniaDTO.add(zaliczenieDTO);
        }
        return zaliczeniaDTO;
    }
    
    public boolean edytujDaneStudenta(int studentId, String imie, String nazwisko) {
        return finder.setPersonalData(studentId, imie, nazwisko);
    }
    
   public boolean wystawOcene(int idZaliczenia, int ocenaId)	//TU PAMIETAJ ZEBY PRZEKAZAC nrIndexu A NIE id.
   {
	   return finder.setCreditGrade(idZaliczenia, ocenaId);
   }
    
    public StudentDTO przywrocStudenta(int studentId) {
        //TODO public StudentDTO przywrocStudenta(int studentId){
        return null;
    }
    
    public List<OcenaDTO> pobierzOceny(){
        List<Ocena> oceny = finder.findAll(Ocena.class);
        List<OcenaDTO> ocenydto = new ArrayList<OcenaDTO>();
        for(Ocena ocena:oceny){
            ocenydto.add(new OcenaDTO.OcenaDTOBuilder()
                .id(ocena.getId())
                .wysokosc(ocena.getWysokosc())
                .buduj());
        }
        return ocenydto;
    }
    
    public boolean utworzStudenta(String imie, String nazwisko, String wykladIds){
        return finder.utworzStudenta(imie, nazwisko, wykladIds);
    }
    
}
