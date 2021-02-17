package gov.dgipjn.dextra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gov.dgipjn.dextra.model.Fuero;
import gov.dgipjn.dextra.repository.lotus.FueroRepositoryLotusImpl;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
@Service
public class FueroService {
    FueroRepositoryLotusImpl fueroRepository = new FueroRepositoryLotusImpl();

    @GraphQLQuery(name = "fueros")
    public List<Fuero> getAll() {
        return this.fueroRepository.getAll();
    }
}
