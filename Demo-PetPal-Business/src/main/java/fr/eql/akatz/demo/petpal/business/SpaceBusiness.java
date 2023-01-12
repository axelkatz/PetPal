package fr.eql.akatz.demo.petpal.business;

import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;

public interface SpaceBusiness {

    Owner getOwnerUpdatedWithPets(Owner owner);
    Owner getOwnerUpdatedWithFavoritePetCategories(Owner owner);
    void insertCat(Cat cat, Owner owner);
}
