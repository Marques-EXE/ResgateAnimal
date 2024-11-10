package org.example.adotante;

public class Adotante {
    private int id;
    private String nome;
    private int idade;
    private String cpf;
    private String endereco;

    public Adotante(String nome, int idade, String cpf, String endereco) {
        if (idade < 18) {
            throw new IllegalArgumentException("Adotante deve ter no mínimo 18 anos.");
        }
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.endereco = endereco;
    }
    public Adotante(int id, String nome, int idade, String cpf, String endereco) {
        this(nome, idade, cpf, endereco);
        this.id = id;
    }
    public int getId(){return id;}
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 18) {
            throw new IllegalArgumentException("Idade mínima para adoção é 18 anos.");
        }
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Idade: " + idade + ", CPF: " + cpf + ", Endereço: " + endereco;
    }

}

