package com.reports;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Profile("test")
@Configuration
public class TestConfiguration {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    private Morphia morphia() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("com.reports");
        return morphia;
    }

    @Bean
    public Datastore dataStore() {

        ServerAddress address = new ServerAddress("localhost", 27017);
        List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
        MongoCredential credientials = MongoCredential.createCredential("admin", "admin", "admin".toCharArray());
        mongoCredentialList.add(credientials);
        MongoClient mongoClient = new MongoClient(address, mongoCredentialList);
        Datastore dataStore = morphia().createDatastore(mongoClient, "local");
        return dataStore;
    }

}
