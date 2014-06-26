package javahive.api;

import java.util.ArrayList;
import java.util.List;

import javahive.api.dto.OcenaDTO;
import javahive.api.dto.StudentDTO;
import javahive.api.dto.StudentDTOMementoCaretaker;
import javahive.api.dto.WykladDTO;
import javahive.api.dto.ZaliczenieDTO;
import javahive.domain.Ocena;
import javahive.domain.Student;
import javahive.domain.Wyklad;
import javahive.domain.Zaliczenie;
import javahive.infrastruktura.StudentRepo;

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
    private StudentRepo studentRepo;
    private StudentDTOMementoCaretaker mementoCaretaker;
    public List<StudentDTO> getListaWszystkichStudentow() {
    	if(mementoCaretaker==null) 
    	{
    		mementoCaretaker = new StudentDTOMementoCaretaker();
    	}
        List<StudentDTO> studentciDTO=new ArrayList<StudentDTO>();
        for(Student student: studentRepo.findAll(Student.class)) {
            StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
            .imie(student.getImie())
            .nazwisko(student.getNazwisko())
            .wieczny(student.isWieczny())
            .indeks(student.getIndeks())
            .id(student.getId())
            .buduj();
            studentciDTO.add(studentDTO);
            mementoCaretaker.setMemento(studentDTO.createMemento());
        }
        return studentciDTO;
    }
    
    //Dodatkowa funkcjonalnosc
    public List<WykladDTO> getListaWszystkichWykladow() {
        List<WykladDTO> wykladyDTO=new ArrayList<WykladDTO>();
        for(Wyklad wyklad: studentRepo.findAll(Wyklad.class)) {
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
        return studentRepo.findAll(Student.class).size();
    }
    
    public StudentDTO znajdzStudenta(String indexNumber) {
    	if(mementoCaretaker==null)
    	{
    		mementoCaretaker = new StudentDTOMementoCaretaker();
    	}
    	Student student=studentRepo.findStudentWithIndexNumber(indexNumber);
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
        List<StudentDTO> studenciDTO=new ArrayList<StudentDTO>();
    	if(mementoCaretaker==null) 
    	{
    		mementoCaretaker = new StudentDTOMementoCaretaker();
    	}
        for(Student student: studentRepo.findStudentsWithFullName(imie, nazwisko)) {
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
        return studenciDTO;
    }
    
    public List<StudentDTO> znajdzStudentow(String param, int wariant) {
    	List<StudentDTO> studenciDTO=new ArrayList<StudentDTO>();
    	if(mementoCaretaker==null)
    	{
    		mementoCaretaker = new StudentDTOMementoCaretaker();
    	}
    	if(wariant==0)	//param==imie
    	{
            for(Student student: studentRepo.findStudentsWithName(param)) {
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
    	}
    	else	//param==nazwisko
    	{
            for(Student student: studentRepo.findStudentsWithLastName(param)) {
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
    	}
    	return  studenciDTO;
    }
    
    public List<StudentDTO> znajdzStudentowPo(String index, String imie, String nazwisko){
        List<StudentDTO> studenciDTO=new ArrayList<StudentDTO>();
        if(mementoCaretaker==null) 
        {
        	mementoCaretaker = new StudentDTOMementoCaretaker();
        }
        for(Student student: studentRepo.findStudentsBy(index,imie, nazwisko)) {
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
        return studenciDTO;
    }
    
    public boolean usunStudenta(int id) {
        return studentRepo.deleteStudentWithIndexNumber(id);
    }
    
    public List<ZaliczenieDTO> pobierzZaliczenia(int studentId) {
        List<ZaliczenieDTO> zaliczeniaDTO=new ArrayList<ZaliczenieDTO>();
        for(Zaliczenie zaliczenie: studentRepo.findCreditsOfStudent(studentId)) {
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
        return studentRepo.setPersonalData(studentId, imie, nazwisko);
    }
    
   public boolean wystawOcene(int idZaliczenia, int ocenaId)
   {
	   return studentRepo.setCreditGrade(idZaliczenia, ocenaId);
   }
    
    public StudentDTO przywrocStudenta(int studentId) {
    	if(mementoCaretaker==null) 
    	{
    		mementoCaretaker = new StudentDTOMementoCaretaker();
    	}
        return new StudentDTO(mementoCaretaker.getMementoOfStudent(studentId));
    }
    
    public List<OcenaDTO> pobierzOceny(){
        List<Ocena> oceny = studentRepo.findAll(Ocena.class);
        List<OcenaDTO> ocenydto = new ArrayList<OcenaDTO>();
        for(Ocena ocena:oceny){
            ocenydto.add(new OcenaDTO.OcenaDTOBuilder()
                .id(ocena.getId())
                .wysokosc(ocena.getWysokosc())
                .buduj());
        }
        return ocenydto;
    }
    
    public boolean utworzStudenta(String imie, String nazwisko, int[] wykladIds){
        try
        {
        	return studentRepo.utworzStudenta(imie, nazwisko, wykladIds);
        }
        catch(Exception e)
        {
        	//Gdybysmy mieli log, to tutaj logowalibysmy informacje z wyjatku
        	return false;
        }
    }
    
}
