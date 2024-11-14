package com.projeto.programacao.animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {
    private Connection connection;

    public AnimalRepository(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void insert(Animal animal) throws SQLException {
        String sql = "insert into animal(nome, especie, raca, idade, castrado) value(?,?,?,?,?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, animal.getNome());
        stmt.setString(2, animal.getEspecie());
        stmt.setString(3, animal.getRaca());
        stmt.setInt(4, animal.getIdade());
        stmt.setBoolean(5, animal.isCastrado());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<Animal> findAll() throws SQLException {
        List<Animal> animais = new ArrayList<>();
        String sql = "select a.id, a.nome, a.especie, a.raca, a.idade, a.castrado from animal a left join adocao d on a.id = d.animal_id where d.animal_id is null";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            String especie = resultSet.getString(3);
            String raca = resultSet.getString(4);
            int idade = resultSet.getInt(5);
            boolean castrado = resultSet.getBoolean(6);
            Animal animal = new Animal(id, nome, especie, raca, idade, castrado);
            animais.add(animal);
        }
        resultSet.close();
        return animais;
    }

    public void delete(int id) throws SQLException {
        String sql = "delete from animal where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();

    }

    public void update(Animal animal) throws SQLException {
        String sql = "update animal set nome = ?, especie = ?, raca = ?, idade = ?, castrado = ? where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, animal.getNome());
        stmt.setString(2, animal.getEspecie());
        stmt.setString(3, animal.getRaca());
        stmt.setInt(4, animal.getIdade());
        stmt.setBoolean(5, animal.isCastrado());
        stmt.setInt(6, animal.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public Optional<Animal> findById(int id) throws SQLException {
        Optional<Animal> animal = Optional.empty();
        String sql = "select nome, especie, raca, idade, castrado from animal where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            String nome = resultSet.getString(1);
            String especie = resultSet.getString(2);
            String raca = resultSet.getString(3);
            int idade = resultSet.getInt(4);
            boolean castrado = resultSet.getBoolean(5);
            animal = Optional.of(new Animal(id, nome, especie, raca, idade, castrado));
        }
        resultSet.close();
        return animal;
    }

    public List<Animal> findAllCastrados() throws SQLException {
        List<Animal> animais = new ArrayList<>();
        String sql = "select a.id, a.nome, a.especie, a.raca, a.idade, a.castrado from animal a left join adocao d on a.id = d.animal_id where castrado = true and d.animal_id is null";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            String especie = resultSet.getString(3);
            String raca = resultSet.getString(4);
            int idade = resultSet.getInt(5);
            boolean castrado = resultSet.getBoolean(6);
            Animal animal = new Animal(id, nome, especie, raca, idade, castrado);
            animais.add(animal);
        }
        resultSet.close();
        return animais;
    }

    public List<Animal> findAllNaoCastrados() throws SQLException {
        List<Animal> animais = new ArrayList<>();
        String sql = "select a.id, a.nome, a.especie, a.raca, a.idade, a.castrado from animal a left join adocao d on a.id = d.animal_id where castrado = false and d.animal_id is null";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            String especie = resultSet.getString(3);
            String raca = resultSet.getString(4);
            int idade = resultSet.getInt(5);
            boolean castrado = resultSet.getBoolean(6);
            Animal animal = new Animal(id, nome, especie, raca, idade, castrado);
            animais.add(animal);
        }
        resultSet.close();
        return animais;
    }
}

