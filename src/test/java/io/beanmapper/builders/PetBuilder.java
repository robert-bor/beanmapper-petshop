package io.beanmapper.builders;

import io.beanmapper.model.Pet;
import io.beanmapper.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetBuilder extends AbstractBuilder<Pet> {

    @Autowired
    public PetBuilder(PetRepository petRepository) {
        super(petRepository, Pet::new);
    }

    public PetBuilder name(String name) {
        entity.setName(name);
        return this;
    }
}
