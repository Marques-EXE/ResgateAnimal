package org.example.funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioRepository {
    private Connection connection;
    public FuncionarioRepository(String url, String username, String password)throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
    public void insert(Funcionario funcionario)throws SQLException{
        String sql = "insert into funcionario(nome, idade, endereco, cpf) value(?,?,?,?)";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, funcionario.getNome());
        stmt.setInt(2, funcionario.getIdade());
        stmt.setString(3, funcionario.getEndereco());
        stmt.setString(4, funcionario.getCpf());
        stmt.executeUpdate();
        stmt.close();
    }
    public List<Funcionario> findAll()throws SQLException{
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "select id, nome, idade, endereco, cpf from funcionario";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String nome = resultSet.getString(2);
            int idade = resultSet.getInt(3);
            String endereco = resultSet.getString(4);
            String cpf = resultSet.getString(5);
            Funcionario funcionario = new Funcionario(id, nome, idade, endereco, cpf);
            funcionarios.add(funcionario);
        }
        resultSet.close();
        return funcionarios;
    }
    public void delete(int id) throws SQLException {
        String sql = "delete from funcionario where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();

    }
    public Optional<Funcionario> findById(int id)throws SQLException{
        Optional<Funcionario> funcionario = Optional.empty();
        String sql = "select nome, idade, endereco, cpf from funcionario where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()){
            String nome = resultSet.getString(1);
            int idade = resultSet.getInt(2);
           String endereco = resultSet.getString(3);
            String cpf = resultSet.getString(4);
            funcionario = Optional.of(new Funcionario(id, nome, idade, endereco, cpf));
        }
        resultSet.close();
        return funcionario;
    }
    public void update(Funcionario funcionario) throws SQLException {
        String sql = "update funcionario set nome = ?, idade = ?, endereco = ?, cpf = ? where id = ?";
        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.setString(1, funcionario.getNome());
        stmt.setInt(2, funcionario.getIdade());
        stmt.setString(3, funcionario.getEndereco());
        stmt.setString(4, funcionario.getCpf());
        stmt.setInt(5, funcionario.getId());
        stmt.executeUpdate();
        stmt.close();
    }
}
