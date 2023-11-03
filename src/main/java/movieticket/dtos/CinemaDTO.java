package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Cinema;

public class CinemaDTO {

	private Long id;
	private String address;
	
	private List<Long> moviesIds = new ArrayList<>();
	private List<Long> roomsIds = new ArrayList<>();
	
	public CinemaDTO() {}
	
	public CinemaDTO(Cinema entity) {
		this.id = entity.getId();
		this.address = entity.getAddress();
		
		entity.getMovies().forEach(movie -> this.moviesIds.add(movie.getId()));
		entity.getRooms().forEach(room -> this.roomsIds.add(room.getId()));
	}

	public CinemaDTO(Long id, String address) {
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

	public List<Long> getMoviesIds() {
		return moviesIds;
	}

	public List<Long> getRoomsIds() {
		return roomsIds;
	}
}
