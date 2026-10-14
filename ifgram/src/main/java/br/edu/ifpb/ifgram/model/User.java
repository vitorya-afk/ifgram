package br.edu.ifpb.ifgram.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = true, unique = true)
    private String email;

    public User() {
    }
    public User(String nome, String email){
        this.nome = nome;
        this.email = email;
    }
    public Long getId(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getEmail(){
        return this.email;
    }

    public void setNome(@Size(min = 3) @NotNull String nome) {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }
}