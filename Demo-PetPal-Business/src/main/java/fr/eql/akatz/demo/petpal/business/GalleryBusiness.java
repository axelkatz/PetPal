package fr.eql.akatz.demo.petpal.business;

import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;

import java.util.List;

public interface GalleryBusiness {

	void insertCat(Cat cat, Owner owner);
	boolean catExists(Cat cat);
	int countCatsByOwner(Owner owner);
	Owner getOwnerUpdatedWithPets(Owner owner);
	List<Owner> findAllOwnersButSelf(Owner self);
}
