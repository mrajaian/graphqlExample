package com.mimr.graphqlExample.recordschedule;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLInitializer {
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("userById", getUserInfo()))
                .build();
    }


    public DataFetcher getUserInfo() {

        // Actual datasource call
        List<ProgramInfo> programInfos = List.of(new ProgramInfo("id1", "userId1", "programId1", false),
                new ProgramInfo("id2", "userId2", "programId2", false),
                new ProgramInfo("id3", "userId1", "programId3", true));

        return dataFetchingEnvironment -> {
            String userId = dataFetchingEnvironment.getArgument("id");
            List<ProgramInfo> favPrograms = programInfos
                    .stream()
                    .filter(programInfo -> programInfo.getUserId().equals(userId))
                    .collect(Collectors.toList());
            return new User(userId, "name", "USA", favPrograms.toArray(new ProgramInfo[favPrograms.size()]));
        };
    }
}
