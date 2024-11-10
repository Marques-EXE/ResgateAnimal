package com.projeto.programacao.adotante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdotanteRepository {
    private Connection connection;
    public AdotanteRepository(String url, String username, String password)throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
    public void insert(Adotante adotante)throws SQLException{
        String sql = "insert into adotante (nome, idade, endereco, cpf, telefone) value(?,?,?,?,?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, adotante.getNome());
        stmt.setInt(2, adotante.getIdade());
        stmt.setString(3, adotante.getEndereco());
        stmt.setString(4, adotante.getCpf());
        stmt.setString(5, adotante.getTelefone());
        stmt.executeUpdate();
        stmt.close();
    }
    public List<Adotante> findAll()throws SQLException{
        List<Adotante> adotantes = new ArrayList<>();
        String sql = "select id, nome, idade, endereco, cpf, telefone from adotante";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            int idade = resultSet.getInt(3);
            String endereco = resultSet.getString(4);
            String cpf = resultSet.getString(5);
            String telefone = resultSet.getString(6);
            Adotante adotante = new Adotante(id, nome, idade, cpf, endereco, telefone);
            adotantes.add(adotante);
        }
        resultSet.close();
        return adotantes;
    }
    public void delete(String cpf) throws SQLException {
        String sql = "delete from adotante where cpf = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        stmt.execute();
        stmt.close();

    }
    public Optional<Adotante> findByCpf(String cpf)throws SQLException{
        Optional<Adotante> adotante = Optional.empty();
        String sql = "select id, nome, idade, endereco, telefone from adotante where cpf = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1,cpf);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            int idade = resultSet.getInt(3);
            String endereco = resultSet.getString(4);
            String telefone = resultSet.getString(5);
            adotante = Optional.of(new Adotante(id, nome, idade, cpf, endereco, telefone));
        }
        resultSet.close();
        return adotante;
    }
    public void update(Adotante adotante) throws SQLException {
        String sql = "update adotante set nome = ?, idade = ?, endereco = ?, cpf = ?, telefone = ? where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, adotante.getNome());
        stmt.setInt(2, adotante.getIdade());
        stmt.setString(3, adotante.getEndereco());
        stmt.setString(4, adotante.getCpf());
        stmt.setString(5, adotante.getTelefone());
        stmt.setInt(6, adotante.getId());
        stmt.executeUpdate();
        stmt.close();
    }
}
