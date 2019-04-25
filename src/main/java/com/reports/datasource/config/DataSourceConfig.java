package com.reports.datasource.config;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mongodb.MongoClient;
import com.reports.util.Constants;

@Configuration
public class DataSourceConfig {

    @Value("${" + Constants.MORPHIA_MAP_PACKAGES + ":com.reports}")
    public String mapPackages;

    @Autowired
    private MongoProperties mongoProperties;

    private Morphia morphia() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage(mapPackages);
        return morphia;
    }

    @Bean
    public Datastore datastore(MongoClient mongoClient) {
        final Datastore datastore = morphia().createDatastore(mongoClient, mongoProperties.getDatabase());
        datastore.ensureIndexes();
        return datastore;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PolymorphicEventDataModule", new Version(1, 0, 0, null, null, null));
        mapper.registerModule(module);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,
                true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        mapper.setDefaultPropertyInclusion(Include.NON_NULL);
        return mapper;
    }

}
