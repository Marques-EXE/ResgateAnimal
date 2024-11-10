package org.example.adocao;
import org.example.adotante.Adotante;
import org.example.animal.Animal;
import org.example.funcionario.Funcionario;
import java.sql.SQLException;
import java.util.List;

public class AdocaoService {
    private AdocaoRepository adocoes;
    public AdocaoService(AdocaoRepository adocoes){
        this.adocoes = adocoes;
    }

    public void registrarAdocao(Animal animal, Adotante adotante, Funcionario funcionario) throws SQLException {
        this.adocoes.insert(new Adocao(animal, adotante, funcionario));
    }

    public List <Adocao> listarHistoricoAdocoes() throws SQLException {
        return this.adocoes.findAll();


    }
}
