package fr.eql.akatz.demo.petpal.dao;

import fr.eql.akatz.demo.petpal.entity.Owner;

import java.util.List;

public interface OwnerDao {

    Owner authenticate(String login, String password);
    List<Owner> findAllButSelf(Owner self);
}
