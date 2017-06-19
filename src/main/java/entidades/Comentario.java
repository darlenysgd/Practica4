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
    private int likes;
    private int dislikes;


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

    @OneToOne
    private Usuario autor;


    public Comentario() {
    }

    public Comentario(String comentatio, Usuario autor) {
        this.id = id;
        this.comentatio = comentatio;
        this.autor = autor;

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

}
