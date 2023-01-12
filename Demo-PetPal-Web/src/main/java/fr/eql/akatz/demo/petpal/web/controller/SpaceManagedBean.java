package fr.eql.akatz.demo.petpal.web.controller;

import fr.eql.akatz.demo.petpal.business.SpaceBusiness;
import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.CatBreed;
import fr.eql.akatz.demo.petpal.entity.Owner;
import fr.eql.akatz.demo.petpal.entity.Pet;
import fr.eql.akatz.demo.petpal.web.util.DateUtils;
import fr.eql.akatz.demo.petpal.web.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ManagedBean(name = "mbSpace")
@RequestScoped
public class SpaceManagedBean implements Serializable {

	@ManagedProperty(value = "#{mbLogin.connectedOwner}")
	private Owner connectedOwner;

	private Pet selectedPet;
	private List<String> catBreeds = new ArrayList<>();
	@Size(min = 1, message = "Veuillez entrer un nom")
	private String newCatName;
	@NotNull(message="Veuillez sélectionner une race")
	private String newCatBreed;
	@NotNull(message="Veuillez entrer une date de naissance")
	private Date newCatBirthDate;

	@EJB
	private SpaceBusiness spaceBusiness;

	public SpaceManagedBean() {
	}

	@PostConstruct
	public void init() {
		connectedOwner = spaceBusiness.getOwnerUpdatedWithPets(connectedOwner);
		selectedPet = connectedOwner.getPets().get(0);
		connectedOwner = spaceBusiness.getOwnerUpdatedWithFavoritePetCategories(connectedOwner);
		catBreeds = Stream.of(CatBreed.values())
				.map(breed -> StringUtils.firstLetterCapitalized(breed.toString()))
				.collect(Collectors.toList());
	}

	public List<String> caseCorrectedFavoritePets() {
		return connectedOwner.getFavoritePetCategories().stream().map(
				pet -> StringUtils.firstLetterCapitalized(pet.toString())
		).collect(Collectors.toList());
	}

	public void updateSelectedPet(Pet pet) {
		selectedPet = pet;
	}

	public String fullDate(LocalDate date) {
		return DateUtils.fullDate(date);
	}

	public String catBreed(Pet pet) {
		Cat cat = (Cat) pet;
		return StringUtils.firstLetterCapitalized(cat.getBreed().toString());
	}

	public void insertCat() {
		Cat newCat = new Cat(
				null,
				newCatName,
				CatBreed.valueOf(newCatBreed.toUpperCase()),
				newCatBirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				"pictures/cat.jpg");
		spaceBusiness.insertCat(newCat, connectedOwner);
		init();
	}

	public Owner getConnectedOwner() {
		return connectedOwner;
	}
	public void setConnectedOwner(Owner connectedOwner) {
		this.connectedOwner = connectedOwner;
	}
	public Pet getSelectedPet() {
		return selectedPet;
	}
	public void setSelectedPet(Pet selectedPet) {
		this.selectedPet = selectedPet;
	}
	public List<String> getCatBreeds() {
		return catBreeds;
	}
	public String getNewCatName() {
		return newCatName;
	}
	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
	public String getNewCatBreed() {
		return newCatBreed;
	}
	public void setNewCatBreed(String newCatBreed) {
		this.newCatBreed = newCatBreed;
	}
	public Date getNewCatBirthDate() {
		return newCatBirthDate;
	}
	public void setNewCatBirthDate(Date newCatBirthDate) {
		this.newCatBirthDate = newCatBirthDate;
	}
}
