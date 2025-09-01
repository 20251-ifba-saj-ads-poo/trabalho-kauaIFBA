package br.edu.ifba.saj.fwads.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Autor extends AbstractEntity {
    @Column
    @NotBlank
    @Size(min = 5)
    private String nome;
    @Column
    @Email
    private String email;
    @Column
    @NotBlank
    @Size(min = 11, max = 11)
    private String CPF;
    

    
    public Autor(@NotBlank @Size(min = 5) String nome, @Email String email,
            @NotBlank @Size(min = 11, max = 11) String cPF) {
        this.nome = nome;
        this.email = email;
        CPF = cPF;
    }
    public Autor() {
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String cPF) {
        CPF = cPF;
    }
    @Override
    public String toString() {
        return "Autor [nome=" + nome + ", email=" + email + ", CPF=" + CPF + "]";
    }       
    
}
