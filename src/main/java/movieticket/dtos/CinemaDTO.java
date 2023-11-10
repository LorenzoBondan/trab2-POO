package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Cinema;

public class CinemaDTO {

	private Long id;
	private String name;
	private String address;
	
	private List<Long> moviesIds = new ArrayList<>();
	private List<Long> roomsIds = new ArrayList<>();
	
	public CinemaDTO() {}
	
	public CinemaDTO(Cinema entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.address = entity.getAddress();
		
		entity.getMovies().forEach(movie -> this.moviesIds.add(movie.getId()));
		entity.getRooms().forEach(room -> this.roomsIds.add(room.getId()));
	}

	public CinemaDTO(Long id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Cinema [código=" + id + ", nome=" + name + ", endereço=" + address + "]";
	}
	
	public String toStringWithList() {
		return "Cinema [código=" + id + ", nome=" + name + ", endereço=" + address + ", códigos dos filmes=" + moviesIds
				+ ", códigos das salas=" + roomsIds + "]";
	}
}
