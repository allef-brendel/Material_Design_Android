package com.example.macaxeiratec.domain;

public class Personagens {

    public String nome;
    public String clan;
    public int foto;

    public Personagens(String nome, String clan, int foto) {
        this.nome = nome;
        this.clan = clan;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}