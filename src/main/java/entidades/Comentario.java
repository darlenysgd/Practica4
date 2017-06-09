package entidades;

/**
 * Created by darle on 6/3/2017.
 */
public class Comentario {

    private long id;
    private String comentatio;
    private Usuario autor;
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
