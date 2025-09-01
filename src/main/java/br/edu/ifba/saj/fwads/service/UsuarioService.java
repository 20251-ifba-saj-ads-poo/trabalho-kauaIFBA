package br.edu.ifba.saj.fwads.service;

import java.util.Map;
import java.util.NoSuchElementException;

import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Usuario;

public class UsuarioService extends Service<Usuario> {

    public UsuarioService() {
        super(Usuario.class);
    }

    public Usuario validaLogin(String login, String senha) throws LoginInvalidoException {
        try {
            return findByMap(Map.of("login", login, "senha", senha)).getFirst();
        } catch (NoSuchElementException e) {
            throw new LoginInvalidoException(
                "Não foi possível localizar o usuário " + login + ", ou a senha esta errada");
        }
    }
}
