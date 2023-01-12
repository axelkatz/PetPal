package fr.eql.akatz.demo.petpal.web.controller;

import fr.eql.akatz.demo.petpal.business.GalleryBusiness;
import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.Owner;
import fr.eql.akatz.demo.petpal.web.util.DateUtils;
import fr.eql.akatz.demo.petpal.web.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "mbGallery")
@ViewScoped
public class GalleryManagedBean implements Serializable {

	@ManagedProperty(value = "#{mbLogin.connectedOwner}")
	private Owner connectedOwner;

	private List<Owner> owners;
	private Owner selectedOwner;
	private Cat selectedCat;

	@EJB
	private GalleryBusiness galleryBusiness;
	
	@PostConstruct
	public void init() {
		owners = findAllOwnersButSelf(connectedOwner);
	}

	public List<Owner> findAllOwnersButSelf(Owner self) {
		return galleryBusiness.findAllOwnersButSelf(self);
	}

	public Owner getOwnerUpdatedWithPets(Owner owner) {
		if (owner != null) {
			return galleryBusiness.getOwnerUpdatedWithPets(owner);
		}
		return null;
	}

	public String caseCorrectedSelectedCatBreed() {
		return StringUtils.firstLetterCapitalized(selectedCat.getBreed().toString());
	}

	public String selectedCatFullBirthDate() {
		return DateUtils.fullDate(selectedCat.getBirthDate());
	}

	public void resetSelectedCat() {
		selectedCat = null;
	}

	public Owner getConnectedOwner() {
		return connectedOwner;
	}
	public void setConnectedOwner(Owner connectedOwner) {
		this.connectedOwner = connectedOwner;
	}
	public List<Owner> getOwners() {
		return owners;
	}
	public Owner getSelectedOwner() {
		return selectedOwner;
	}
	public void setSelectedOwner(Owner selectedOwner) {
		this.selectedOwner = selectedOwner;
	}
	public Cat getSelectedCat() {
		return selectedCat;
	}
	public void setSelectedCat(Cat selectedCat) {
		this.selectedCat = selectedCat;
	}
}
