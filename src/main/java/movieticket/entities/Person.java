package movieticket.entities;

import java.util.Objects;

public class Person {

	private Long id;
	private String name;
	private Person married;
	
	public Person() {}

	public Person(Long id, String name, Person married) {
		super();
		this.id = id;
		this.name = name;
		this.married = married;
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

	public void setName(String name) {
		this.name = name;
	}

	public Person getMarried() {
		return married;
	}

	public void setMarried(Person married) {
		this.married = married;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(id, other.id);
	}
}
