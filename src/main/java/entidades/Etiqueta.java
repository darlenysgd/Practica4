package entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by darle on 6/3/2017.
 */
@Entity
public class Etiqueta {

    @Id
    private String etiqueta;

    public Etiqueta() {
    }

    public Etiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
