package javahive.api.dto;

import javahive.domain.Przedmiot;
import javahive.domain.Wykladowca;

public class WykladDTO {
    private String wykladowca;
    private String przedmiot;
    private WykladDTO(WykladDTOBuilder wykladDTOBuilder){
    	this.wykladowca= wykladDTOBuilder.wykladowca;
    	this.przedmiot= wykladDTOBuilder.przedmiot;
    }
    
    public static class WykladDTOBuilder{
    	private String wykladowca;
    	private String przedmiot;
    	public WykladDTOBuilder() {
		}
    	public WykladDTOBuilder wykladowca(Wykladowca wykladowca)
    	{
    		StringBuilder stringBuilder= new StringBuilder()
    		.append(wykladowca.getStopien())
    		.append(" ")
    		.append(wykladowca.getImie())
    		.append(" ")
    		.append(wykladowca.getNazwisko());
    		this.wykladowca= stringBuilder.toString();
    		return this;
    	}
    	public WykladDTOBuilder przedmiot(Przedmiot przedmiot) {
    		this.przedmiot= przedmiot.getNazwa();
    		return this;
    	}
    	public WykladDTO build(){
    		return new WykladDTO(this);
    	}
    }
}
