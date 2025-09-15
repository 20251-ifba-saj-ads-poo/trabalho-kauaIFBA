package br.edu.ifba.saj.fwads.exception;

// Exceção referente usuários que não possuem autorização para realizar determinada tarefa
public class UserNotAuthorized extends Exception {
    public UserNotAuthorized(String msg) {
        super(msg);
    }
}
