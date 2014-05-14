package javahive.api;

import javahive.api.dto.*;
import javahive.domain.Student;
import javahive.domain.Wyklad;
import javahive.domain.Zaliczenie;
import javahive.infrastruktura.Finder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

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
            StudentDTO studentDTO=new StudentDTO();
            studentDTO.setImie(student.getImie());
            studentDTO.setNazwisko(student.getNazwisko());
            studentDTO.setWieczny(student.isWieczny());
            studentDTO.setId(student.getId());
            if(student.getIndeks()!=null){
                studentDTO.setNumerIndeksu(student.getIndeks().getNumer());
            }
            studentciDTO.add(studentDTO);
        }
        return studentciDTO;
    }


    public int getLiczbaStudentow(){
        return finder.findAll(Student.class).size();
    }
    
    public StudentDTO getStudent(int studentId){
        //TODO public StudentDTO getStudent(int studentId){
        return null;
    }
    
    public List<StudentDTO> znajdzStudentow(String imie, String nazwisko, String nimerIndeksu){
        //TODO public List<StudentDTO> znajdzStudentow(String imie, String nazwisko, String nimerIndeksu){
        return null;
    }
    
    public boolean usunStudenta(int studentId){
        //TODO public boolean usunStudenta(int studentId){
        return false;
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
