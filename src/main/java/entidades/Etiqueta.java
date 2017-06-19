package entidades;

import javax.persistence.*;

/**
 * Created by darle on 6/3/2017.
 */
@Entity
public class Etiqueta {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
