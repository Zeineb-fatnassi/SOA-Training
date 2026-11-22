package graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import repository.ModuleBusiness;
import repository.UniteEnseignementBusiness;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndPoint extends SimpleGraphQLServlet {

    public GraphQLEndPoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        UniteEnseignementBusiness uniteEnseignementBusiness = new UniteEnseignementBusiness();
        ModuleBusiness moduleBusiness = new ModuleBusiness();
        return SchemaParser.newParser()
                .file("schema.graphql")
                .resolvers(new Query(moduleBusiness, uniteEnseignementBusiness),
                        new Mutations(moduleBusiness, uniteEnseignementBusiness))
                .build()
                .makeExecutableSchema();
    }
}
