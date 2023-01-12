package fr.eql.akatz.demo.petpal.business.impl;

import fr.eql.akatz.demo.petpal.business.GalleryBusiness;
import fr.eql.akatz.demo.petpal.dao.CatDao;
import fr.eql.akatz.demo.petpal.dao.OwnerDao;
import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Remote(GalleryBusiness.class)
@Stateless
public class GalleryBusinessImpl implements GalleryBusiness {

    @EJB
    private CatDao catDao;

    @EJB
    private OwnerDao ownerDao;

    @Override
    public void insertCat(Cat cat, Owner owner) {
        catDao.insertCat(cat, owner);
    }

    @Override
    public boolean catExists(Cat cat) {
        return catDao.exists(cat);
    }

    @Override
    public int countCatsByOwner(Owner owner) {
        return catDao.countByOwner(owner);
    }

    @Override
    public Owner getOwnerUpdatedWithPets(Owner owner) {
        owner.getPets().addAll(catDao.findByOwner(owner));
        return owner;
    }

    @Override
    public List<Owner> findAllOwnersButSelf(Owner self) {
        return ownerDao.findAllButSelf(self);
    }
}
