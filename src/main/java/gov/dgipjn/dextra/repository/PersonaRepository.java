package gov.dgipjn.dextra.repository;

import java.util.List;

import gov.dgipjn.dextra.model.Persona;

public interface PersonaRepository {
    public List<Persona> getAll();
    public Persona getById();
}
