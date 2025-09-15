package br.edu.ifba.saj.fwads.exception;

// Exceção para usuários já inscritos em um encontro
public class UserAlreadySubscribed extends Exception{
    public UserAlreadySubscribed(String msg){
        super(msg);
    }
}
