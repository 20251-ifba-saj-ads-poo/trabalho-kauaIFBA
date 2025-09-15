package br.edu.ifba.saj.fwads.exception;

// Exceção para login inválido
public class LoginInvalidoException extends Exception {
    public LoginInvalidoException(String msg){
        super(msg);
    }    
}
