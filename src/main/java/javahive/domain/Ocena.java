package javahive.domain;

import javax.persistence.Entity;

import javahive.infrastruktura.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Ocena extends BaseEntity {
 public Ocena(){};
 
 private String wysokosc; //NOSONAR

}
