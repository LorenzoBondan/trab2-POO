package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

public class ActorDTO extends PersonDTO {

	private List<Long> moviesIds = new ArrayList<>();
	
	public ActorDTO(Long id, String name, Long marriedId) {
		super(id, name, marriedId);
	}

	public List<Long> getMoviesIds() {
		return moviesIds;
	}
}
