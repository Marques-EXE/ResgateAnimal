package org.example.funcionario;
import org.example.animal.Animal;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FuncionarioService {
    private FuncionarioRepository funcionarios;
    public FuncionarioService(FuncionarioRepository funcionarios){
        this.funcionarios = funcionarios;
    }

    public void adicionarFuncionario(Funcionario funcionario) throws SQLException {
        this.funcionarios.insert(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return this.funcionarios.findAll();
    }



    public Optional <Funcionario> buscarFuncionarioPorId(int id) throws SQLException {
        return this.funcionarios.findById(id);
    }

    public void removerFuncionario(int id) throws SQLException {
        this.funcionarios.delete(id);

}
    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        this.funcionarios.update(funcionario);
    }
    }