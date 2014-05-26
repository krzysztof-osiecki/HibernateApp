package javahive.api.dto;

import javahive.domain.Ocena;
import javahive.domain.Wyklad;

public class ZaliczenieDTO {
    private String ocena;
    private String przedmiot;
    private String wykladowca;
    private ZaliczenieDTO(ZaliczenieDTOBuilder zaliczenieDTOBuilder){
    	;
    }
    
    public static class ZaliczenieDTOBuilder {
        private String ocena;
        private String przedmiot;
        private String wykladowca;
    	public ZaliczenieDTOBuilder(){
    	}
    	
    	public ZaliczenieDTOBuilder ocena(Ocena ocena){
    		this.ocena=ocena.getWysokosc();
    		return this;
    	}
    	
    	public ZaliczenieDTOBuilder wyklad(Wyklad wyklad){
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
    	
    	public ZaliczenieDTO build(){
    		return new ZaliczenieDTO(this);
    	}
    }
}
