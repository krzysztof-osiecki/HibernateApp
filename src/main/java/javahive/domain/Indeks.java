package javahive.domain;

import java.util.List;

import javax.persistence.*;

import javahive.infrastruktura.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Data
@Entity
@ToString
public class Indeks extends BaseEntity {
    public Indeks(){};
    @SuppressWarnings("unused")
    private String numer;
    
    @OneToOne(mappedBy = "indeks")
    @SuppressWarnings("unused")
    private Student student;
    
    @OneToMany(mappedBy="indeks", fetch = FetchType.EAGER) 
    @SuppressWarnings("unused")
    private List<Zaliczenie> zaliczenia;
}
