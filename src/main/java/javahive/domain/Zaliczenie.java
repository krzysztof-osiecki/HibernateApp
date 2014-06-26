package javahive.domain;

import javahive.infrastruktura.BaseEntity;

import javax.persistence.*;

import lombok.*;
//Test
@Entity
@Getter
@Setter
public class Zaliczenie extends BaseEntity{
    public Zaliczenie(){};
    
    @ManyToOne(optional=true)
    @JoinColumn(name="indeks_id")
    private Indeks indeks; //NOSONAR
    
    @ManyToOne
    @JoinColumn(name="wyklad_id",referencedColumnName="id")
    private Wyklad wyklad; //NOSONAR
    
    @ManyToOne(optional=true)
    @JoinColumn(name="ocena_id")
    private Ocena ocena; //NOSONAR
}
