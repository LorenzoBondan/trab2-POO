package movieticket.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cinema {

	private Long id;
	private String address;
	
	private List<Movie> movies = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	
	public Cinema() {}

	public Cinema(Long id, String address) {
		super();
		this.id = id;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cinema other = (Cinema) obj;
		return Objects.equals(id, other.id);
	}
}
