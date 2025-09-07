package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.exception.CpfUniquenessException;
import br.edu.ifba.saj.fwads.exception.EmptyFieldException;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
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
                    "Não foi possível localizar o usuário com o cpf '" + cpf + "', ou a senha esta incorreta.");
        }
    }

    // Verificar se CPF está no formato válido
    public void validaFieldFormat(String name, String cpf, String senha) throws IncorretFormatException {
        if(cpf.length() != 11 || cpf.isBlank()) {
            throw new IncorretFormatException("Campo CPF inválido.");
        }
        if(senha.length() < 5 || senha.isBlank()) {
            throw new IncorretFormatException("Campo senha inválido.");
        }
        if(name.length() < 5 || name.isBlank()) {
            throw new IncorretFormatException("Campo nome inválido");
        }
    }

    // Verificar se todos campos estão preenchidos
    public void validaEmptyField(String name, String cpf, String senha) throws EmptyFieldException {
        if (name.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            throw new EmptyFieldException("Preencha todos campos para continuar.");
        }

    }


    public void validaUniquenessCpf(String cpf) throws CpfUniquenessException {
            if(!findByMap(Map.of("cpf", cpf)).isEmpty()){ // O MÉTODO FIND BY MAP RETORNA UMA LISTA, DÁ PARA ANALISAR SE A LISTA ESTÁ VAZIA PARA VALIDAR SE EXISTE OCORRÊNCIA
                throw new CpfUniquenessException("Já existe uma conta vinculada à este CPF.");
            }
    }
}
