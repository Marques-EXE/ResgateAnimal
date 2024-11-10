package com.projeto.programacao.adotante;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdotanteService {
   private AdotanteRepository adotantes;
   public AdotanteService(AdotanteRepository adotantes){
       this.adotantes = adotantes;
   }

    public void adicionarAdotante(Adotante adotante) throws SQLException {
        this.adotantes.insert(adotante);
    }

    public List<Adotante> listarAdotantes() throws SQLException {
        return
        this.adotantes.findAll();
    }


    public Optional <Adotante> buscarAdotantePorCpf(String cpf) throws SQLException {
        return this.adotantes.findByCpf(cpf);
    }

    public void removerAdotante(String cpf) throws SQLException {
       this.adotantes.delete(cpf);

    }
    public void atualizarAdotante(Adotante adotante) throws SQLException {
       this.adotantes.update(adotante);
    }
}
