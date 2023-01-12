package fr.eql.akatz.demo.petpal.dao;

import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;

import java.util.List;

public interface CatDao {

    void insertCat(Cat cat, Owner owner);
    boolean exists(Cat cat);
    int countByOwner(Owner owner);
    List<Cat> findByOwner(Owner owner);
}
