package javahive.domain;

import java.util.List;

import javahive.infrastruktura.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.google.common.collect.Lists;

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
	//TODO:Tam gdzie mappedBy to import w yamlu
	//@OneToMany(mappedBy="student",fetch=FetchType.EAGER)
    //@OneToMany
	//private List<Ocena> oceny=Lists.newArrayList();
	
    @OneToOne//(mappedBy="student",fetch=FetchType.EAGER)
    @JoinColumn(name = "indeks_id",referencedColumnName="id")
    private Indeks indeks;
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