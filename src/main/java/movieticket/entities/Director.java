package movieticket.entities;

import java.util.ArrayList;
import java.util.List;

public class Director extends Person{

	private List<Movie> movies = new ArrayList<>();
	
	public Director(Long id, String name, String role, Person married) {
		super(id, name, role, married);
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Director [movies=" + movies + "]";
	}
}
