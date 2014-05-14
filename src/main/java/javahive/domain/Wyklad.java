package javahive.domain;

import java.util.List;

import javahive.infrastruktura.BaseEntity;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@Entity
public class Wyklad extends BaseEntity {
    public Wyklad(){};
    
    @ManyToOne
    @JoinColumn(name="wykladowca_id", referencedColumnName="id")
    private Wykladowca wykladowca;
    
    @ManyToOne
    @JoinColumn(name="przedmiot_id", referencedColumnName="id")
    private Przedmiot przedmiot;  
    
    @OneToMany(mappedBy="wyklad")
    private List<Zaliczenie> zaliczenia;
}
