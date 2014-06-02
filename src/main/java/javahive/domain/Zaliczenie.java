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
    private Indeks indeks;
    
    @ManyToOne
    @JoinColumn(name="wyklad_id",referencedColumnName="id")
    private Wyklad wyklad;
    
    @ManyToOne
    @JoinColumn(name="ocena_id",referencedColumnName="id")
    private Ocena ocena;
}
