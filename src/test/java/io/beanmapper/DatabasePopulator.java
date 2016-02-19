package io.beanmapper;

import io.beanmapper.builders.PetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabasePopulator implements org.springframework.jdbc.datasource.init.DatabasePopulator {

    @Autowired private PetBuilder petBuilder;

    @Override
    public void populate(Connection connection) throws SQLException, ScriptException {
        petBuilder.name("Loebas").save();
    }
}
