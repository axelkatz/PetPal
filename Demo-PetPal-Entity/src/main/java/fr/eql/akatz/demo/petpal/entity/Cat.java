package fr.eql.akatz.demo.petpal.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Cat extends Pet implements Serializable {
	
	private Long id;
	private final CatBreed breed;
	
	public Cat(Long id, String name, CatBreed breed, LocalDate birthDate, String picture) {
		super(null, name, birthDate, picture);
		this.id = id;
		this.breed = breed;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CatBreed getBreed() {
		return breed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cat cat = (Cat) o;
		return id.equals(cat.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
