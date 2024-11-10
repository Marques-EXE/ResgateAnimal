package org.example.animal;

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade; // em meses
    private boolean castrado;

    public Animal(String nome, String especie, String raca, int idade, boolean castrado) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.castrado = castrado;
    }
    public Animal(int id, String nome, String especie, String raca, int idade, boolean castrado) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.castrado = castrado;
    }
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Espécie: " + especie + ", Raça: " + raca +
                ", Idade: " + idade + " meses, Castrado: " + (castrado ? "Sim" : "Não");
    }

}


