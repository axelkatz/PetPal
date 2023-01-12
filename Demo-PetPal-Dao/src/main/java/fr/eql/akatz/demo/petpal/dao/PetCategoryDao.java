package fr.eql.akatz.demo.petpal.dao;

import fr.eql.akatz.demo.petpal.entity.Owner;
import fr.eql.akatz.demo.petpal.entity.PetCategory;

import java.util.List;

public interface PetCategoryDao {

    List<PetCategory> findByOwner(Owner owner);
}
