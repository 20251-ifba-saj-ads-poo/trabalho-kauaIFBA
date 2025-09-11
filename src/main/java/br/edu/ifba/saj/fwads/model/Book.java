package br.edu.ifba.saj.fwads.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
public class Book extends AbstractEntity {

    @Column
    @NotBlank
    @Size(min = 5)
    private String title;

    @Column
    @NotBlank
    @Size(min = 5)
    private String author;

    @Column
    @NotBlank
    @Size(min = 5)
    private String genre;

    @Column
    @NotBlank
    @Size(min = 5)
    private String synopsis;

    @Column
    @NotBlank
    @Size(min = 5)
    private String publisher;

    @Column
    @NotBlank
    private LocalDate publicationYear;

    public Book(@NotBlank @Size(min = 5) String title,@NotBlank @Size(min = 5) String author,@NotBlank @Size(min = 5) String genre,@NotBlank @Size(min = 5) String synopsis,@NotBlank @Size(min = 5) String publisher, LocalDate publicationYear) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.synopsis = synopsis;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }

    public Book() {

    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getSynopsis() { return synopsis; }
    public String getPublisher() { return publisher; }
    public LocalDate getPublicationYear() { return publicationYear; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setPublicationYear(LocalDate publicationYear) { this.publicationYear = publicationYear; }

    @Override
    public String toString() {
        return title +
                "\nescrito por: " + author +
                "\n" + genre +
                "\n" + publisher +
                "\npublicado em:" + publicationYear +
                "\n" + synopsis;
    }
}
