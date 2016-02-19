package io.beanmapper;

import io.beanmapper.builders.PetBuilder;
import io.beanmapper.builders.PetTypeBuilder;
import io.beanmapper.model.Pet;
import io.beanmapper.model.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabasePopulator implements org.springframework.jdbc.datasource.init.DatabasePopulator {

    @Autowired private PetBuilder petBuilder;
    @Autowired private PetTypeBuilder petTypeBuilder;

    @Override
    public void populate(Connection connection) throws SQLException, ScriptException {
        PetType dog = petTypeBuilder.type("Dog").familyName("Canidae").save();
        PetType cat = petTypeBuilder.type("Cat").familyName("Felidae").save();
        PetType mouse = petTypeBuilder.type("Mouse").familyName("Muridae").save();

        petBuilder.nickname("Loebas").age(3).sex(Pet.Sex.MALE).type(dog).save();
        petBuilder.nickname("Snuf").age(5).sex(Pet.Sex.MALE).type(dog).save();
        petBuilder.nickname("garfield").age(16).sex(Pet.Sex.FEMALE).type(cat).save();
        petBuilder.nickname("jerry").age(1).sex(Pet.Sex.NEUTRAL).type(mouse).save();
    }
}
