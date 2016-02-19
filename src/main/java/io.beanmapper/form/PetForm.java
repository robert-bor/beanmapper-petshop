package io.beanmapper.form;

import io.beanmapper.annotations.BeanProperty;

public class PetForm {

    public String nickname;
    public int age;
    public String sex;
    @BeanProperty(name = "type.id")
    public Long petTypeId;
}
