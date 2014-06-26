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
    
    private String numer;
    
    @OneToOne(mappedBy = "indeks")//, fetch=FetchType.EAGER)
    //@JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;
    
    @OneToMany(mappedBy="indeks", fetch = FetchType.EAGER)    
    private List<Zaliczenie> zaliczenia;
}
