package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

public class DirectorDTO extends PersonDTO{

	private List<Long> moviesIds = new ArrayList<>();
	
	public DirectorDTO(Long id, String name, Long marriedId) {
		super(id, name, marriedId);
	}
	
	public List<Long> getMoviesIds() {
		return moviesIds;
	}
}
