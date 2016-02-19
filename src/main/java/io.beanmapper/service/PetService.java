package io.beanmapper.service;

import io.beanmapper.model.Pet;
import io.beanmapper.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired private PetRepository petRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet create(Pet newPet) {
        return petRepository.save(newPet);
    }
}
