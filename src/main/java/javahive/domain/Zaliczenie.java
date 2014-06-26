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
    @SuppressWarnings("unused")
    private Indeks indeks;
    
    @ManyToOne
    @JoinColumn(name="wyklad_id",referencedColumnName="id")
    @SuppressWarnings("unused")
    private Wyklad wyklad;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="ocena_id")
    @SuppressWarnings("unused")
    private Ocena ocena;
}
