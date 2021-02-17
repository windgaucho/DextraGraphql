package gov.dgipjn.dextra.service;

import java.util.List;

import gov.dgipjn.dextra.model.Categoria;
import gov.dgipjn.dextra.repository.lotus.CategoriaRepositoryLotusImpl;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
@Service
public class CategoriaService {
    CategoriaRepositoryLotusImpl categoriaRepository = new CategoriaRepositoryLotusImpl();

    @GraphQLQuery(name = "categorias")
    public List<Categoria> getAll() {
        return this.categoriaRepository.getAll();
    }
}
