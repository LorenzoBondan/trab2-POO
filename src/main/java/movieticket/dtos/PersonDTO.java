package movieticket.dtos;

import movieticket.entities.Person;

public class PersonDTO {

	private Long id;
	private String name;
	private String role;
	private Long marriedId;
	
	public PersonDTO() {}
	
	public PersonDTO(Person entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.role = entity.getRole();
		if(entity.getMarried() != null) {
			this.marriedId = entity.getMarried().getId();
		}
	}

	public PersonDTO(Long id, String name, String role, Long marriedId) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.marriedId = marriedId;
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

	public Long getMarriedId() {
		return marriedId;
	}

	public void setMarriedId(Long marriedId) {
		this.marriedId = marriedId;
	}

	@Override
	public String toString() {
		return "Pessoa [código=" + id + ", nome=" + name + ", papel=" + role + ", Código do cônjuge=" + marriedId + "]";
	}
}
