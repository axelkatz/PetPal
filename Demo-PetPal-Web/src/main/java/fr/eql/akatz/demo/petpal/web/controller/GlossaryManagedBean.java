package fr.eql.akatz.demo.petpal.web.controller;

import fr.eql.akatz.demo.petpal.business.GlossaryBusiness;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "mbGlossary")
@ViewScoped
public class GlossaryManagedBean implements Serializable {

    private List<String> expressions;
    private String selectedExpression = "";

    @EJB
    GlossaryBusiness glossaryBusiness;

    @PostConstruct
    public void init() {
        expressions = glossaryBusiness.findGlossary();
        if (!expressions.isEmpty()) {
            selectedExpression = expressions.get(0);
        }
    }

    public String fetchExtract() {
        return glossaryBusiness.fetchExtract(selectedExpression);
    }

    public void updateSelectedExpression(String expression) {
        selectedExpression = expression;
    }

    public List<String> getExpressions() {
        return expressions;
    }
    public String getSelectedExpression() {
        return selectedExpression;
    }
    public void setSelectedExpression(String selectedExpression) {
        this.selectedExpression = selectedExpression;
    }
}
