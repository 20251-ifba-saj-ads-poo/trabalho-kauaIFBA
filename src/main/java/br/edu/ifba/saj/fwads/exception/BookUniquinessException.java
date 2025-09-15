package br.edu.ifba.saj.fwads.exception;

// Exceção para livro único
public class BookUniquinessException extends Throwable {
    public BookUniquinessException(String msg) {
        super(msg);
    }
}
