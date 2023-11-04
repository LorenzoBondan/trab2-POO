package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Gender;

public class GenderDTO {

	private Long id;
	private String name;
	
	private List<Long> moviesIds = new ArrayList<>();
	
	public GenderDTO() {}
	
	public GenderDTO(Gender entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		
		entity.getMovies().forEach(movie -> this.moviesIds.add(movie.getId()));
	}

	public GenderDTO(Long id, String name) {
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

	public List<Long> getMoviesIds() {
		return moviesIds;
	}

	@Override
	public String toString() {
		return "GenderDTO [id=" + id + ", name=" + name + "]";
	}
	
	public String toStringWithList() {
		return "GenderDTO [id=" + id + ", name=" + name + ", moviesIds=[" + moviesIds + "]";
	}
}
