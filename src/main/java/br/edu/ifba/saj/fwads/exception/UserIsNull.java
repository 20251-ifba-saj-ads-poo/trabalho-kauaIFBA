package br.edu.ifba.saj.fwads.exception;

// Exceção para usuários nulos
public class UserIsNull extends Exception {
     public UserIsNull(String msg){
        super(msg);
    }
}
