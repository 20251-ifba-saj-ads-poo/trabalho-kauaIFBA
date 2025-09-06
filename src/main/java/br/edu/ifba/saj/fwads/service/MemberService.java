package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Member;

import java.util.Map;
import java.util.NoSuchElementException;

public class MemberService extends Service<Member> {

    public MemberService() {
        super(Member.class);
    }

    public Member validaLogin(String cpf, String senha) throws LoginInvalidoException {
        try {
            return findByMap(Map.of("cpf", cpf, "password", senha)).getFirst();
        } catch (NoSuchElementException e) {
            throw new LoginInvalidoException(
                    "Não foi possível localizar o usuário " + cpf + ", ou a senha esta errada");
        }
    }
}
