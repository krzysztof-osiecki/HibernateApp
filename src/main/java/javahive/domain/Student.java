package javahive.domain;

import javahive.infrastruktura.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;


@Getter
@Setter
@Entity

//Definicja ustala na jakich atrybutach/polach encji filtr ma działać
@FilterDefs({
	@FilterDef(	name="FILTER_TEST_STUDENT_NAZWISKO", 
				parameters=@ParamDef( name="PARAM_student_Nazwisko", type="string" ) ),
	@FilterDef(	name="FILTER_TEST_STUDENT_ID", 
				parameters=@ParamDef( name="PARAM_student_ID", type="int" ) )
})

// Fitlry określają jaki ma zostać spełniony warunek na zadanych w definicji parametrach
@Filters({
	@Filter(name = "FILTER_TEST_STUDENT_NAZWISKO", condition = "lower(nazwisko) like :PARAM_student_Nazwisko"),
	@Filter(name = "FILTER_TEST_STUDENT_ID", condition = "id > :PARAM_student_ID")
})


public class Student extends BaseEntity {
	public Student(){};

	private String imie;
	private String nazwisko;
	private boolean wieczny;
	
    @OneToOne
    @JoinColumn(name = "indeks_id",referencedColumnName="id")
    private Indeks indeks;
    private Student(StudentBuilder studentBuilder) {
    	this.imie = studentBuilder.imie;
    	this.nazwisko = studentBuilder.nazwisko;
    	this.indeks = studentBuilder.indeks;
    	this.wieczny = studentBuilder.wieczny;
    }
    
    
    public static class StudentBuilder {
    	private String imie;
    	private String nazwisko;
        private Indeks indeks;
    	private boolean wieczny;
    	
    	public StudentBuilder() {
    	}
    	
    	public StudentBuilder imie(String imie) {
    		this.imie = imie;
    		return this;
    	}
    	
    	public StudentBuilder nazwisko(String nazwisko) {
    		this.nazwisko = nazwisko;
    		return this;
    	}
    	
    	public StudentBuilder indeks(Indeks  indeks) {
    		this.indeks = indeks;
    		return this;
    	}
    	
    	public StudentBuilder wieczny(boolean wieczny) {
    		this.wieczny = wieczny;
    		return this;
    	}
    	
    	public Student buduj() {
    		return new Student(this);
    	}
    	
    }
    
    public static class StudentMemento {
    	private String imie;
    	private String nazwisko;
        private Indeks indeks;
    	private boolean wieczny;
    	public void zapamietaj(){
    		
    	}
    }
}
/*
- Student(adam1):
    imie: Adam
    nazwisko: Abramek1
    wieczny: false
    
- Student(bartek):
    imie: Bartek
    nazwisko: Bielecki
    wieczny: true

- Student(cezary):
    imie: Cezary
    nazwisko: Cap
    wieczny: false

- Student(dariusz):
    imie: Dariusz
    nazwisko: Dziewulski
    wieczny: false

- Student(edward):
    imie: Edward
    nazwisko: Eklerek
    wieczny: true

- Student(grzegorz):
    imie: Grzegorz
    nazwisko: Grabowski
    wieczny: false

- Student(henryk):
    imie: Henryk
    nazwisko: Heller
    wieczny: false
- Student(ireneusz):
    imie: Ireneusz
    nazwisko: Ikarus
    wieczny: false
- Student(jedrzej):
    imie: Jędrzej
    nazwisko: Jaworowski
    wieczny: true
- Student(natan):
    imie: Natan
    nazwisko: Nowak
    wieczny: false
    indeks: Indeks(4)
*/