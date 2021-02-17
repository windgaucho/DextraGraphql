package gov.dgipjn.dextra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gov.dgipjn.dextra.model.Persona;
import gov.dgipjn.dextra.repository.lotus.PersonaRepositoryLotusImpl;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
@Service
public class PersonaService {
    PersonaRepositoryLotusImpl personaRepository = new PersonaRepositoryLotusImpl();

    @GraphQLQuery(name = "personas")
    public List<Persona> getAll() {
        return this.personaRepository.getAll();
    }
}