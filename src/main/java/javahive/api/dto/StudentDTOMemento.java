package javahive.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import javahive.domain.Indeks;

/**
 * Created by m on 29.04.14.
 */

@Getter
@Setter
@ToString

public class StudentDTOMemento implements Serializable{
    private String imie;
    private String nazwisko;
    private boolean wieczny;
    private String numerIndeksu;
    private int id;
    public StudentDTOMemento(StudentDTO studentDTO) {
    	this.imie = studentDTO.getImie();
    	this.nazwisko = studentDTO.getNazwisko();
    	this.numerIndeksu = studentDTO.getNumerIndeksu();
    	this.wieczny = studentDTO.isWieczny();
    	this.id = studentDTO.getId();
    }
}
