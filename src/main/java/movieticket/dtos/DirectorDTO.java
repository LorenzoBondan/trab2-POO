package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Director;

public class DirectorDTO extends PersonDTO{

	private List<Long> moviesIds = new ArrayList<>();
	
	public DirectorDTO(Long id, String name, String role, Long marriedId) {
		super(id, name, role, marriedId);
	}
	
	public DirectorDTO(Director entity) {
		this.setId(entity.getId());
		this.setName(entity.getName());
		this.setRole(entity.getRole());
		this.setMarriedId(entity.getMarried().getId());
		entity.getMovies().forEach(movie -> this.moviesIds.add(movie.getId()));
	}
	
	public List<Long> getMoviesIds() {
		return moviesIds;
	}

	@Override
	public String toString() {
		return "DirectorDTO [id=" + this.getId() + ", name=" + this.getName() + ", role=" + this.getRole() + ", moviesIds=" + moviesIds + "]";
	}
}
