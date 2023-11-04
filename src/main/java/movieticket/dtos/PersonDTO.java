package movieticket.dtos;

import movieticket.entities.Person;

public class PersonDTO {

	private Long id;
	private String name;
	private Long marriedId;
	
	public PersonDTO() {}
	
	public PersonDTO(Person entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		if(entity.getMarried() != null) {
			this.marriedId = entity.getMarried().getId();
		}
	}

	public PersonDTO(Long id, String name, Long marriedId) {
		super();
		this.id = id;
		this.name = name;
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

	public Long getMarriedId() {
		return marriedId;
	}

	public void setMarriedId(Long marriedId) {
		this.marriedId = marriedId;
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", name=" + name + ", marriedId=" + marriedId + "]";
	}
}
