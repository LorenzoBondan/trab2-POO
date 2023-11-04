package movieticket.entities;

import java.util.Objects;

public class Person {

	private Long id;
	private String name;
	private String role; // to identify if is Actor or Director
	private Person married;
	
	public Person() {}

	public Person(Long id, String name, String role, Person married) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.married = married;
	}
	
	public Person(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", role =" + role + ", married=" + married + "]";
	}
}
