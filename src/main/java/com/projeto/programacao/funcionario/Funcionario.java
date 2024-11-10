package com.projeto.programacao.funcionario;

public class Funcionario {
    private int id;
    private String nome;
    private int idade;
    private String endereco;
    private String cpf;
    private String telefone;

    public Funcionario(String nome, int idade, String endereco, String cpf, String telefone) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    public Funcionario(int id,String nome, int idade, String endereco, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.cpf = cpf;
        this.telefone = telefone;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Idade: " + idade + ", Endereço: " + endereco + ", CPF: " + cpf;
    }


}