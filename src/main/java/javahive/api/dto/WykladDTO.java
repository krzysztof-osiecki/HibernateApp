package javahive.api.dto;

import lombok.*;
import java.io.Serializable;

import javahive.domain.Przedmiot;
import javahive.domain.Wykladowca;
@ToString
@Getter
@Setter
public class WykladDTO implements Serializable{
    private int id;
    private String wykladowca;
    private String przedmiot;
    private int id;
    private WykladDTO(WykladDTOBuilder wykladDTOBuilder){
        this.id = wykladDTOBuilder.id;
    	this.wykladowca= wykladDTOBuilder.wykladowca;
    	this.przedmiot= wykladDTOBuilder.przedmiot;
    	this.id= wykladDTOBuilder.id;
    }
    
    public static class WykladDTOBuilder{
        private int id;
    	private String wykladowca;
    	private String przedmiot;
    	private int id;
    	
    	public WykladDTOBuilder() {
		}
    	
    	public WykladDTOBuilder id(int id){
    	    this.id = id;
    	    return this;
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
    	
    	public WykladDTOBuilder id(int id)
    	{
    		this.id=id;
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
