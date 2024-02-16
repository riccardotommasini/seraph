package org.streamreasoning.gsp.services;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.streamreasoning.gsp.seraph.engine.Seraph;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;

import java.lang.reflect.InvocationTargetException;
@ComponentScan
@Configuration
public class SeraphConfig {

    @Bean
    public EngineConfiguration configurationBeam() throws ConfigurationException {
        return EngineConfiguration.loadConfig("/seraph.properties");
    }

    @Bean
    public Seraph seraphBean() throws ConfigurationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new Seraph(configurationBeam());
    }

}
