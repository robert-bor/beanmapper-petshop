package io.beanmapper.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Pet extends BaseModel {

    public enum Sex {
        MALE, FEMALE, NEUTRAL, HERMAPHRODITIC
    }

    private String nickname;
    private int age;
    private Sex sex;
    @ManyToOne
    private PetType type;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }
}
