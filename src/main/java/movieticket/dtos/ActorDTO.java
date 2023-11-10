package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Actor;

public class ActorDTO extends PersonDTO {

	private List<Long> moviesIds = new ArrayList<>();
	
	public ActorDTO(Long id, String name, String role, Long marriedId) {
		super(id, name, role, marriedId);
	}
	
	public ActorDTO(Actor entity) {
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
		return "Ator [código=" + this.getId() + ", nome=" + this.getName() + ", papel=" + this.getRole() + "]";
	}
	
	public String toStringWithList() {
		return "Ator [código=" + this.getId() + ", nome=" + this.getName() + ", papel=" + this.getRole() + ", códigos dos filmes=" + moviesIds + "]";
	}
}
