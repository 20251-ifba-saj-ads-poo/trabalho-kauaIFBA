package br.edu.ifba.saj.fwads.exception;

// Exceção para CPF único
public class CpfUniquenessException extends Exception {
    public CpfUniquenessException(String msg){
        super(msg);
    }
}
