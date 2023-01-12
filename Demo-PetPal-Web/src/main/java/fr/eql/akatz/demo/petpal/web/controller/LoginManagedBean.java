package fr.eql.akatz.demo.petpal.web.controller;

import fr.eql.akatz.demo.petpal.business.LoginBusiness;
import fr.eql.akatz.demo.petpal.entity.Owner;

import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "mbLogin")
@SessionScoped
public class LoginManagedBean implements Serializable {
	
	private String login;
	private String password;
	private Owner connectedOwner;

	@EJB
	private LoginBusiness loginBusiness;
	
	public String connect() {
		String forward;
		connectedOwner = loginBusiness.authenticate(login, password);
		if (connectedOwner != null) {
			forward = "/index.xhtml?faces-redirect=true";
		} else {
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Identifiant et/ou mot de passe incorrect(s)",
					"Identifiant et/ou mot de passe incorrect(s)"
					);
			FacesContext.getCurrentInstance().addMessage("loginForm:inpLogin", facesMessage);
			FacesContext.getCurrentInstance().addMessage("loginForm:inpPassword", facesMessage);
			forward = "/login.xhtml?faces-redirect=false";
		}
		return forward;
	}

	public boolean isConnected() {
		return connectedOwner != null;
	}

	public void authorise() {
		FacesContext context = FacesContext.getCurrentInstance();
		ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler)
				context.getApplication().getNavigationHandler();
		if (!isConnected()) {
			handler.performNavigation("/login.xhtml?faces-redirect=true");
		}
	}

	public String disconnect() {
		HttpSession session = (HttpSession) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSession(true);
		session.invalidate();
		login = "";
		password = "";
		connectedOwner = null;
		return "/index.xhtml?faces-redirect=true";
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Owner getConnectedOwner() {
		return connectedOwner;
	}
	public void setConnectedOwner(Owner connectedOwner) {
		this.connectedOwner = connectedOwner;
	}
}
