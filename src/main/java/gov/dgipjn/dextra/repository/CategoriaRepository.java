package gov.dgipjn.dextra.repository;

import java.util.List;

import gov.dgipjn.dextra.model.Categoria;

public interface CategoriaRepository {
    public List<Categoria> getAll();
    public Categoria getById(String id);
}
