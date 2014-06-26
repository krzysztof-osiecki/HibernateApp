package javahive.api.dto;

import java.io.Serializable;
import lombok.*;

import javahive.domain.Ocena;
import javahive.domain.Wyklad;

@ToString
@Getter
@Setter

public class ZaliczenieDTO implements Serializable{
    private int id;
    private String ocena;
    private String przedmiot;
    private String wykladowca;
    public ZaliczenieDTO(){};
    private ZaliczenieDTO(ZaliczenieDTOBuilder zaliczenieDTOBuilder) {
        this.id = zaliczenieDTOBuilder.id;
     this.ocena = zaliczenieDTOBuilder.ocena;
     this.przedmiot = zaliczenieDTOBuilder.przedmiot;
     this.wykladowca = zaliczenieDTOBuilder.wykladowca;
    }
    
    public static class ZaliczenieDTOBuilder {
        private int id;
        private String ocena;
        private String przedmiot;
        private String wykladowca;
     public ZaliczenieDTOBuilder() {
     }
     
     public ZaliczenieDTOBuilder id(int id){
         this.id = id;
         return this;
     }
     
     public ZaliczenieDTOBuilder ocena(Ocena ocena) {
         if(ocena!=null){
             this.ocena=ocena.getWysokosc();
         }
      return this;
     }
     
     public ZaliczenieDTOBuilder wyklad(Wyklad wyklad) {
      this.przedmiot=wyklad.getPrzedmiot().getNazwa();
      StringBuilder stringBuilder=new StringBuilder()
      .append(wyklad.getWykladowca().getStopien())
      .append(" ")
      .append(wyklad.getWykladowca().getImie())
      .append(" ")
      .append(wyklad.getWykladowca().getNazwisko());
      this.wykladowca=stringBuilder.toString();
      return this;
     }
     
     public ZaliczenieDTO buduj() {
      return new ZaliczenieDTO(this);
     }
    }
}
