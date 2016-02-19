package io.beanmapper.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;

import javax.sql.DataSource;

@Configuration
@Profile("data")
public class DataProfile {

    @Autowired private DataSource dataSource;
    @Autowired private DatabasePopulator databasePopulator;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        return dataSourceInitializer;
    }
}
