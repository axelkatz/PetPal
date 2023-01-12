package fr.eql.akatz.demo.petpal.business.impl;

import fr.eql.akatz.demo.petpal.business.LoginBusiness;
import fr.eql.akatz.demo.petpal.dao.OwnerDao;
import fr.eql.akatz.demo.petpal.entity.Owner;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(LoginBusiness.class)
@Stateless
public class LoginBusinessImpl implements LoginBusiness {

    @EJB
    private OwnerDao ownerDao;

    @Override
    public Owner authenticate(String login, String password) {
        return ownerDao.authenticate(login, password);
    }
}
