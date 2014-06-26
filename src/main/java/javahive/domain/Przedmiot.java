package javahive.domain;

import java.util.List;

import javax.persistence.*;

import javahive.infrastruktura.BaseEntity;
import lombok.*;


@Entity
@Getter
@Setter
public class Przedmiot extends BaseEntity {
    public Przedmiot(){};
    
	private String nazwa; //NOSONAR
	
	@OneToMany(mappedBy = "przedmiot")
	private List<Wyklad> wyklady;
}
