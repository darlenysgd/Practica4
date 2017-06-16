package entidades;

import javax.persistence.*;

/**
 * Created by darle on 6/3/2017.
 */
@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String comentatio;


    @OneToOne
    private Usuario autor;
    @OneToOne
    private Articulo articulo;


    public Comentario() {
    }

    public Comentario(String comentatio, Usuario autor, Articulo articulo) {
        this.id = id;
        this.comentatio = comentatio;
        this.autor = autor;
        this.articulo = articulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentatio() {
        return comentatio;
    }

    public void setComentatio(String comentatio) {
        this.comentatio = comentatio;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
