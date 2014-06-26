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
    
    private String stopien; //NOSONAR
    private String imie; //NOSONAR
    private String nazwisko; //NOSONAR
    
    @OneToMany(mappedBy="wykladowca")
    private List<Wyklad> wyklady; //NOSONAR
}
