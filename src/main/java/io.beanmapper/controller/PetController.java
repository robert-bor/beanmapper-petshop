package io.beanmapper.controller;

import io.beanmapper.BeanMapper;
import io.beanmapper.form.PetForm;
import io.beanmapper.model.Pet;
import io.beanmapper.result.PetNameResult;
import io.beanmapper.result.PetResult;
import io.beanmapper.service.PetService;
import io.beanmapper.spring.web.MergedForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired private BeanMapper beanMapper;
    @Autowired private PetService petService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<PetNameResult> findAll() {
        return beanMapper.map(petService.findAll(), PetNameResult.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PetResult findOne(@PathVariable Long id) {
        return beanMapper.map(petService.findOne(id), PetResult.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Pet create(@MergedForm(value = PetForm.class) Pet pet) {
        return petService.save(pet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Pet update(@MergedForm(value = PetForm.class, mergeId = "id") Pet pet) {
        return petService.save(pet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        petService.delete(id);
    }
}
