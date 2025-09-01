package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario extends AbstractEntity {

    @Column
    @NotBlank
    @Size(min = 5)
    private String senha;
    @Column
    @NotBlank
    @Size(min = 5)
    private String login;
    @Column
    @Email
    private String email;

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Usuario [senha=" + senha + ", login=" + login + ", email=" + email + "]";
    }

    

}
