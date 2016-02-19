package io.beanmapper.builders;

import io.beanmapper.model.Pet;
import io.beanmapper.model.PetType;
import io.beanmapper.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetBuilder extends AbstractBuilder<Pet> {

    @Autowired
    public PetBuilder(PetRepository petRepository) {
        super(petRepository, Pet::new);
    }

    public PetBuilder nickname(String nickname) {
        entity.setNickname(nickname);
        return this;
    }

    public PetBuilder age(int age) {
        entity.setAge(age);
        return this;
    }

    public PetBuilder sex(Pet.Sex sex) {
        entity.setSex(sex);
        return this;
    }

    public PetBuilder type(PetType petType) {
        entity.setType(petType);
        return this;
    }
}
