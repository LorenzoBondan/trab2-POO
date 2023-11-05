package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

public class ActorDTO extends PersonDTO {

	private List<Long> moviesIds = new ArrayList<>();
	
	public ActorDTO(Long id, String name, String role, Long marriedId) {
		super(id, name, role, marriedId);
	}

	public List<Long> getMoviesIds() {
		return moviesIds;
	}

	@Override
	public String toString() {
		return "ActorDTO [moviesIds=" + moviesIds + "]";
	}
}
