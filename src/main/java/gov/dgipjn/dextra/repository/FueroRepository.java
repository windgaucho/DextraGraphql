package gov.dgipjn.dextra.repository;

import java.util.List;

import gov.dgipjn.dextra.model.Fuero;

public interface FueroRepository {
    public List<Fuero> getAll();
    public Fuero getById();
}
