package io.beanmapper.controller;

import io.beanmapper.BeanMapper;
import io.beanmapper.result.PetResult;
import io.beanmapper.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Collection<PetResult> findAll() {
        return beanMapper.map(petService.findAll(), PetResult.class);
    }
}
