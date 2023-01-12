package fr.eql.akatz.demo.petpal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner implements Serializable {

	private Long id;
	private final String name;
	private final String surname;
	private final String login;
	private final String password;
	private List<Pet> pets = new ArrayList<>();
	private List<PetCategory> favoritePetCategories = new ArrayList<>();

	public Owner(Long id, String name, String surname, String login, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	public List<PetCategory> getFavoritePetCategories() {
		return favoritePetCategories;
	}
	public void setFavoritePetCategories(List<PetCategory> favoritePetCategories) {
		this.favoritePetCategories = favoritePetCategories;
	}
}
