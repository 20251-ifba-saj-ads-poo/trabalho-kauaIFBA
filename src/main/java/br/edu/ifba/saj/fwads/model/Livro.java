package br.edu.ifba.saj.fwads.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Livro  extends AbstractEntity {
    @Column
    @NotBlank
    @Size(min = 5)
    private String titulo;
    @Column
    @NotBlank
    @Size(min = 5)
    private String subTitulo;
    @Column
    @NotBlank
    @Size(min = 5)
    private String ISBN;
    @ManyToOne
    private Autor autor;

    public Livro(@NotBlank @Size(min = 5) String titulo, @NotBlank @Size(min = 5) String subTitulo,
            @NotBlank @Size(min = 5) String iSBN, Autor autor) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        ISBN = iSBN;
        this.autor = autor;
    }

    public Livro() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro [titulo=" + titulo + ", subTitulo=" + subTitulo + ", ISBN=" + ISBN + ", autor=" + autor + "]";
    }
}
