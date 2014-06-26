package javahive.domain;

import java.util.List;

import javax.persistence.*;

import javahive.infrastruktura.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Data
@Entity
@ToString
public class Indeks extends BaseEntity {
    public Indeks(){};
    
    private String numer; //NOSONAR
    
    @OneToOne(mappedBy = "indeks")
    private Student student; //NOSONAR
    
    @OneToMany(mappedBy="indeks", fetch = FetchType.EAGER)    
    private List<Zaliczenie> zaliczenia; //NOSONAR
}
