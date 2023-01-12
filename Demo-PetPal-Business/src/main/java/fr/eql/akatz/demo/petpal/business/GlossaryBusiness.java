package fr.eql.akatz.demo.petpal.business;

import java.util.List;

public interface GlossaryBusiness {

    List<String> findGlossary();
    String fetchExtract(String expression);
}
