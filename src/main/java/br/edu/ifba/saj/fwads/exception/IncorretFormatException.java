package br.edu.ifba.saj.fwads.exception;

// Exceção genérica referente formato inválido de dados
public class IncorretFormatException extends Exception {
    public IncorretFormatException(String msg){
        super(msg);
    }
}
