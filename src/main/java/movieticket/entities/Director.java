package movieticket.entities;

import java.util.ArrayList;
import java.util.List;

public class Director extends Person{

	private List<Movie> movies = new ArrayList<>();
	
	public Director(Long id, String name, Person married) {
		super(id, name, married);
	}

	public List<Movie> getMovies() {
		return movies;
	}
}
