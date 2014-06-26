package javahive.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by m on 29.04.14.
 */

@Getter
@Setter
@ToString

public final class OcenaDTO implements Serializable{
    private int id;
    private String wysokosc;
    private OcenaDTO(OcenaDTOBuilder ocenaDTOBuilder) {
        this.wysokosc = ocenaDTOBuilder.wysokosc;
        this.id = ocenaDTOBuilder.id;
    }
    
    public static class OcenaDTOBuilder {
        private int id;
        private String wysokosc;
        public OcenaDTOBuilder() {
        }
        
        public OcenaDTOBuilder wysokosc(String wysokosc) {
            this.wysokosc = wysokosc;
            return this;
        }
        public OcenaDTOBuilder id(int id) {
            this.id = id;
            return this;
        }
        
        public OcenaDTO buduj() {
            return new OcenaDTO(this);
        }
        
    }
}
