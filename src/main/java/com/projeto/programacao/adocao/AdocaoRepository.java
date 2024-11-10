package com.projeto.programacao.adocao;

import com.projeto.programacao.adotante.Adotante;
import com.projeto.programacao.animal.Animal;
import com.projeto.programacao.funcionario.Funcionario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdocaoRepository {

    private Connection connection;
    public AdocaoRepository(String url, String username, String password)throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
    public void insert(Adocao adocao)throws SQLException{
        String sql = "insert into adocao (funcionario_id, adotante_id, animal_id) value(?,?,?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        int funcionarioId = adocao.getFuncionario().getId();
        int adotanteId = adocao.getAdotante().getId();
        int animalId = adocao.getAnimal().getId();
        stmt.setInt(1, funcionarioId);
        stmt.setInt(2, adotanteId);
        stmt.setInt(3, animalId);

        stmt.executeUpdate();
        stmt.close();
    }
    public List<Adocao> findAll() throws SQLException {
        List<Adocao> adocoes = new ArrayList<>();
        String sql = "select \n" +
                "\tf.nome nome_funcionario, f.idade idade_funcionario, f.endereco endereco_funcionario, f.cpf cpf_funcionario, f.id id_funcionario,\n" +
                "\ta.id id_adotante, a.nome nome_adotante, a.idade idade_adotante, a.endereco endereco_adotante, a.cpf cpf_adotante,\n" +
                "\tm.nome nome_animal, m.especie especie_animal, m.raca raca_animal, m.idade idade_animal, m.castrado castrado_animal, m.id id_animal,  x.data_adocao data_adocao\n" +
                "from adocao x inner join funcionario f on x.funcionario_id = f.id\n" +
                "inner join adotante a on x.adotante_id = a.id\n" +
                "inner join animal m on x.animal_id = m.id";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()){
            String nomeFuncionario = resultSet.getString(1);
            int idadeFuncionaro = resultSet.getInt(2);
            String enderecoFuncionario = resultSet.getString(3);
            String cpfFuncionario = resultSet.getString(4);
            int idFuncionario = resultSet.getInt(5);
            Funcionario funcionario = new Funcionario(idFuncionario, nomeFuncionario, idadeFuncionaro, enderecoFuncionario, cpfFuncionario);

            int idAdotante = resultSet.getInt(6);
            String nomeAdotante = resultSet.getString(7);
            int idadeAdotante = resultSet.getInt(8);
            String enderecoAdotante = resultSet.getString(9);
            String cpfAdotante = resultSet.getString(10);
            Adotante adotante = new Adotante(idAdotante, nomeAdotante, idadeAdotante, cpfAdotante, enderecoAdotante);

            String nomeAnimal = resultSet.getString(11);
            String especieAnimal = resultSet.getString(12);
            String racaAnimal = resultSet.getString(13);
            int idadeAnimal = resultSet.getInt(14);
            boolean castradoAnimal = resultSet.getBoolean(15);
            int idAnimal = resultSet.getInt(16);
            Animal animal = new Animal(idAnimal, nomeAnimal, especieAnimal, racaAnimal, idadeAnimal, castradoAnimal);
            LocalDateTime dataAdocao = resultSet.getTimestamp(17).toLocalDateTime();
            Adocao adocao = new Adocao(animal, adotante, funcionario, dataAdocao);
            adocoes.add(adocao);
        }
        resultSet.close();
        return adocoes;
    }
}
