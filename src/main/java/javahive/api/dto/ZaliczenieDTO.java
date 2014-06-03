package javahive.api.dto;

import java.io.Serializable;
import lombok.*;

import javahive.domain.Ocena;
import javahive.domain.Wyklad;

@ToString
@Getter
@Setter

public class ZaliczenieDTO implements Serializable{
    private String ocena;
    private String przedmiot;
    private String wykladowca;
    private int id;
    private ZaliczenieDTO(ZaliczenieDTOBuilder zaliczenieDTOBuilder) {
    	this.ocena = zaliczenieDTOBuilder.ocena;
    	this.przedmiot = zaliczenieDTOBuilder.przedmiot;
    	this.wykladowca = zaliczenieDTOBuilder.wykladowca;
    	this.id = zaliczenieDTOBuilder.id;
    }
    
    public static class ZaliczenieDTOBuilder {
        private String ocena;
        private String przedmiot;
        private String wykladowca;
        private int id;
        
    	public ZaliczenieDTOBuilder() {
    	}
    	
    	public ZaliczenieDTOBuilder id(int id) {
    		this.id=id;
    		return this;
    	}
    	
    	public ZaliczenieDTOBuilder ocena(Ocena ocena) {
    		this.ocena=ocena.getWysokosc();
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
