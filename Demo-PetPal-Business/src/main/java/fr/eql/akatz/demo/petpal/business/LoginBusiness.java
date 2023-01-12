package fr.eql.akatz.demo.petpal.business;

import fr.eql.akatz.demo.petpal.entity.Owner;

public interface LoginBusiness {

	Owner authenticate(String login, String password);

}
