package movieticket.entities;

import java.util.ArrayList;
import java.util.List;

public class Actor extends Person{
	
	private List<Movie> movies = new ArrayList<>();

	public Actor(Long id, String name, String role, Person married) {
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
		return "Actor [movies=" + movies + "]";
	}
}
