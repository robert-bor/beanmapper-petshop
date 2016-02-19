package io.beanmapper.builders;

import io.beanmapper.model.PetType;
import io.beanmapper.repository.PetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetTypeBuilder extends AbstractBuilder<PetType> {

    @Autowired
    public PetTypeBuilder(PetTypeRepository petTypeRepository) {
        super(petTypeRepository, PetType::new);
    }

    public PetTypeBuilder type(String type) {
        entity.setType(type);
        return this;
    }

    public PetTypeBuilder familyName(String familyName) {
        entity.setFamilyName(familyName);
        return this;
    }
}
