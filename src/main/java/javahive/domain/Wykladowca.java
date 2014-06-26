package javahive.domain;

import java.util.List;

import javax.persistence.*;

import javahive.infrastruktura.BaseEntity;

import javax.persistence.Entity;

import lombok.*;

@Getter
@Setter
@Entity
public class Wykladowca extends BaseEntity{
    public Wykladowca(){};
    @SuppressWarnings("unused")
    private String stopien;
    @SuppressWarnings("unused")
    private String imie;
    @SuppressWarnings("unused")
    private String nazwisko;
    
    @SuppressWarnings("unused")
    @OneToMany(mappedBy="wykladowca")
    private List<Wyklad> wyklady;
}
