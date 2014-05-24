package javahive.api;

import java.util.ArrayList;
import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.api.dto.WykladDTO;
import javahive.api.dto.ZaliczenieDTO;
import javahive.domain.Student;
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
    public List<StudentDTO> getListaWszystkichStudentow(){
        List studentciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findAll(Student.class)){
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


    public int getLiczbaStudentow(){
        return finder.findAll(Student.class).size();
    }
    
    public StudentDTO getStudent(int studentId){
    	Student student=finder.findStudentWithID(studentId);
        StudentDTO studentDTO= new StudentDTO.StudentDTOBuilder()
        .imie(student.getImie())
        .nazwisko(student.getNazwisko())
        .wieczny(student.isWieczny())
        .indeks(student.getIndeks())
        .id(student.getId())
        .buduj();
        return studentDTO;
    }
    
    public List<StudentDTO> znajdzStudentow(String imie, String nazwisko){
        List studenciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findStudentsWithFullName(imie, nazwisko)){
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
    
    public List<StudentDTO> znajdzStudentow(String param, int wariant){
    	List studenciDTO=new ArrayList<StudentDTO>();
    	if(wariant==0)	//param==imie
    	{
            for(Student student: finder.findStudentsWithName(param)){
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
            for(Student student: finder.findStudentsWithLastName(param)){
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
    
    public boolean usunStudenta(int studentId){
        return finder.deleteStudentWithID(studentId);
    }
    
    //@RequestMapping...
    public boolean wystawOcene(int studentId, int zaliczenieId){
        //TODO public boolean wystawOcene(int studentId, int zaliczenieId){
        return false;
    }
    
    public List<ZaliczenieDTO> pobierzZaliczenia(int studentId){
        //TODO public List<ZaliczenieDTO> pobierzZaliczenia(int studentId){
        return null;
    }
    
    //Dodatkowa funkcjonalnosc
    public List<WykladDTO> pobierzWyklady(){
        //TODO public List<WykladDTO> pobierzWyklady(){
        return null;
    }
    
    public StudentDTO przywrocStudenta(int studentId){
        //TODO public StudentDTO przywrocStudenta(int studentId){
        return null;
    }

}
