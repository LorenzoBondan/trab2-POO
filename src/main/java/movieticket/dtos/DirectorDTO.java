package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

public class DirectorDTO extends PersonDTO{

	private List<Long> moviesIds = new ArrayList<>();
	
	public DirectorDTO(Long id, String name, String role, Long marriedId) {
		super(id, name, role, marriedId);
	}
	
	public List<Long> getMoviesIds() {
		return moviesIds;
	}
}
