package fr.eql.akatz.demo.petpal.entity;

import java.io.Serializable;

public class Glossary implements Serializable {

    private Long id;
    private final String expression;

    public Glossary(Long id, String expression) {
        this.id = id;
        this.expression = expression;
    }

    public Long getId() {
        return id;
    }
    public String getExpression() {
        return expression;
    }
}
