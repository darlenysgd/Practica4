package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by darle on 6/3/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Articulo.findArticulobyEtiqueta",
                query = "SELECT a FROM Articulo a JOIN a.etiquetas t WHERE t.id = :id"
        )
})
public class Articulo implements Serializable {

    public static   String QUERY_NAME_FIND_ARTICULO_BY_ETIQUETA = "Articulo.findArticulobyEtiqueta";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String titulo;
    @Column(length = 2000)
    private String cuerpo;
    private String fecha;
    private int likes;
    private int dislikes;


    @OneToOne
    private Usuario autor;
    @ManyToMany (fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE})
    private List<Etiqueta> etiquetas ;
    @OneToMany (fetch = FetchType.EAGER)
    private List<Comentario> comentarios;

    public Articulo(String titulo, String cuerpo, Usuario autor, String fecha) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;

    }

    public Articulo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
