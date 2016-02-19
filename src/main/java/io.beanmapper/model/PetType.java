package io.beanmapper.model;

import javax.persistence.Entity;

@Entity
public class PetType extends BaseModel {

    private String type;
    private String familyName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
