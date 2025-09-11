package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.exception.BookUniquinessException;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class BookService  extends Service<Book> {
    public BookService() {
        super(Book.class);
    }

    public Book create(String title, String author, String genre, String synopsis, String publisher, LocalDate publicationYear) throws IncorretFormatException, BookUniquinessException {
        // LIDAR COM ERRO DE PREENCHIMENTO
        List<String> errors = new ArrayList<>();

        if (title == null || title.length() < 5 || title.isBlank()) {
            errors.add("Título inválido: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }
        if (author == null || author.length() < 5 || author.isBlank()) {
            errors.add("Autor inválido: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }
        if (genre == null || genre.length() < 5 || genre.isBlank()) {
            errors.add("Gênero inválido: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }
        if (synopsis == null || synopsis.length() < 5 || synopsis.isBlank()) {
            errors.add("Sinopse inválida: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }
        if (publisher == null || publisher.length() < 5 || publisher.isBlank()) {
            errors.add("Editora inválida: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }
        if (publicationYear == null || publicationYear.isAfter(LocalDate.now())) {
            errors.add("Ano de publicação inválida: não pode estar em branco e não pode ser uma data depois de " + LocalDate.now().toString() + ".");
        }

        if (!errors.isEmpty()) {
            String allErrors = String.join("\n", errors);
            throw new IncorretFormatException(allErrors);
        }

        // LIDAR COM ERRO DE DUPLICIDADE (VAMOS CONSIDERAR UM LIVRO DUPLICADO, CASO TÍTULO, AUTOR, EDITORA E ANO DE PUBLICAÇÃO SEJAM IGUAIS)
        // findByMap retorna uma lista, se após a busca a lista não está vazia, significa que ele encontrou um membro com aquele CPF.
        if(!findByMap(Map.of("title", title, "author", author, "publisher", publisher, "publicationYear", publicationYear)).isEmpty()){
            throw new BookUniquinessException("Este livro já existe.");
        }

        // if(publicationYear.isBefore(LocalDate.now()) <- usar esse código quando formos cadastrar o encontro :)
        Book newBook = new Book(title, author, genre, synopsis, publisher, publicationYear);
        return newBook;
    }
}
