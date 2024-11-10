package org.example.animal;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AnimalService {
    private AnimalRepository jaula;
    public AnimalService(AnimalRepository jaula)throws SQLException {
        this.jaula = jaula;
    }

    public void adicionarAnimal(Animal animal)throws SQLException {
        this.jaula.insert(animal);
    }

    public List<Animal> listarAnimais() throws SQLException {
        return jaula.findAll();
    }

    public List<Animal> listarAnimaisCastrados(boolean castrados) throws SQLException {
        return jaula.findAllCastrados();
    }
    public List<Animal> listarAnimaisNaoCastrados(boolean castrados) throws SQLException{
        return jaula.findAllNaoCastrados();
    }
    public Optional<Animal> buscarAnimalPorId(int id) throws SQLException {
        return jaula.findById(id);
    }

    public void removerAnimal(int id) throws SQLException {
        jaula.delete(id);
    }
    public void atualizarAnimal(Animal animal) throws SQLException {
        jaula.update(animal);
    }
}
