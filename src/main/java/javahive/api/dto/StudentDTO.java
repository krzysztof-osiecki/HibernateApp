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

public class StudentDTO implements Serializable{
    private String imie;
    private String nazwisko;
    private boolean wieczny;
    private String numerIndeksu;
    private int id;
    private StudentDTO(StudentDTOBuilder studentDTOBuilder)
    {
    	this.imie = studentDTOBuilder.imie;
    	this.nazwisko = studentDTOBuilder.nazwisko;
    	this.numerIndeksu = studentDTOBuilder.indeks;
    	this.wieczny = studentDTOBuilder.wieczny;
    	this.id = studentDTOBuilder.id;
    }
    public static class StudentDTOBuilder {
    	private String imie;
    	private String nazwisko;
        private String indeks;
    	private boolean wieczny;
    	private int id;
    	
    	public StudentDTOBuilder(){
    	}
    	
    	public StudentDTOBuilder imie(String imie){
    		this.imie = imie;
    		return this;
    	}
    	
    	public StudentDTOBuilder nazwisko(String nazwisko){
    		this.nazwisko = nazwisko;
    		return this;
    	}
    	
    	public StudentDTOBuilder indeks(Indeks  indeks){
    		this.indeks = indeks.getNumer();
    		return this;
    	}
    	
    	public StudentDTOBuilder wieczny(boolean wieczny){
    		this.wieczny = wieczny;
    		return this;
    	}
    	
    	public StudentDTOBuilder id(int id){
    		this.id = id;
    		return this;
    	}
    	
    	public StudentDTO buduj(){
    		return new StudentDTO(this);
    	}
    	
    	/*
    	 * Przykladowe stworzenie obiektu User.UserBuilder().imie("Kamil").build();
    	 * badz z pelnym wykorzystaniem: 
    	 * User.UserBuilder()
    	 * .imie("Kamil")
    	 * .nazwisko("Kajak")
    	 * .indeks(index)
    	 * .wieczny(true)
    	 * .build();
    	 */
    }
}
