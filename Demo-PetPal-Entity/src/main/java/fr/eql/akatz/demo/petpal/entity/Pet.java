package fr.eql.akatz.demo.petpal.entity;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Pet implements Serializable {

    private Long petId;
    private final String name;
    private final LocalDate birthDate;
    private final String picture;

    public Pet(Long petId, String name, LocalDate birthDate, String picture) {
        this.petId = petId;
        this.name = name;
        this.birthDate = birthDate;
        this.picture = picture;
    }

    public Long getPetId() {
        return petId;
    }
    public void setPetId(Long id) {
        this.petId = id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getPicture() {
        return picture;
    }
}
