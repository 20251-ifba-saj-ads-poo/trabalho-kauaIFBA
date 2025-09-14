package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.exception.CpfUniquenessException;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.exception.UserAlreadySubscribed;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;

import java.util.ArrayList;
import java.util.List;
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

    public void subscribe(Meeting meeting, Member user) throws UserAlreadySubscribed {
        if (meeting == null || user.getSubscribedMeetings().contains(meeting)) {
            throw new UserAlreadySubscribed("Você já se inscreveu neste encontro ou é um moderador!");
        }
    }

    public List<Meeting> returnUserMeetings(Member user) {
        return user.getSubscribedMeetings();
    }

    public String howManySubMeetings(Member user){
        int count = 0;

        for(Meeting meeting : user.getSubscribedMeetings()){
            count++;
        }

        return String.valueOf(count);
    }

    public String howManyUserMeetings(Member user){
        int count = 0;

        for(Meeting meeting : user.getMyMeetings()){
            count++;
        }

        return String.valueOf(count);
    }

    // VALIDAÇÃO DE CRIAÇÃO DE MEMBRO
    public Member create(String name, String cpf, String senha) throws CpfUniquenessException, IncorretFormatException {
        List<String> errors = new ArrayList<>();

        if (cpf == null || cpf.length() != 11 || cpf.isBlank()) {
            errors.add("CPF inválido: deve ter 11 dígitos e não pode estar em branco.");
        }

        if (senha == null || senha.length() < 5 || senha.isBlank()) {
            errors.add("Senha inválida: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }

        if (name == null || name.length() < 5 || name.isBlank()) {
            errors.add("Nome inválido: deve ter no mínimo 5 caracteres e não pode estar em branco.");
        }

        if (!errors.isEmpty()) {
            String allErrors = String.join("\n", errors);
            throw new IncorretFormatException(allErrors);
        }

        // findByMap retorna uma lista, se após a busca a lista não está vazia, significa que ele encontrou um membro com aquele CPF.
        if(!findByMap(Map.of("cpf", cpf)).isEmpty()){
            throw new CpfUniquenessException("Já existe uma conta vinculada à este CPF.");
        }

        Member newUser = new Member(name, cpf, senha);

        return this.create(newUser);
    }
}
