package io.beanmapper.profiles;

import io.beanmapper.config.Profile;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Component
@org.springframework.context.annotation.Profile("test")
public class TestProfile implements Profile {

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(HSQL).setName("dev");
        return builder.build();
    }

    public String hibernateDialect() {
        return HSQLDialect.class.getName();
    }

    public String hibernate2Ddl() {
        return "create-drop";
    }
}
