package fr.eql.akatz.demo.petpal.business.impl;

import fr.eql.akatz.demo.petpal.business.SpaceBusiness;
import fr.eql.akatz.demo.petpal.dao.CatDao;
import fr.eql.akatz.demo.petpal.dao.PetCategoryDao;
import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;

@Remote(SpaceBusiness.class)
@Stateless
public class SpaceBusinessImpl implements SpaceBusiness {

    @EJB
    CatDao catDao;

    @EJB
    PetCategoryDao petCategoryDao;

    @Override
    public Owner getOwnerUpdatedWithPets(Owner owner) {
        owner.setPets(new ArrayList<>());
        owner.getPets().addAll(catDao.findByOwner(owner));
        return owner;
    }

    @Override
    public Owner getOwnerUpdatedWithFavoritePetCategories(Owner owner) {
        owner.getFavoritePetCategories().addAll(petCategoryDao.findByOwner(owner));
        return owner;
    }

    @Override
    public void insertCat(Cat cat, Owner owner) {
        catDao.insertCat(cat, owner);
    }
}
