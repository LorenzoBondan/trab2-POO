package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Room;

public class RoomDTO {

	private Long id;
	private Integer number;
	private Long cinemaId;
	
	private List<Long> schedulesIds = new ArrayList<>();
	private List<Long> seatsIds = new ArrayList<>();
	
	public RoomDTO() {}
	
	public RoomDTO(Room entity) {
		this.id = entity.getId();
		this.number = entity.getNumber();
		this.cinemaId = entity.getCinema().getId();
		
		entity.getSchedules().forEach(schedule -> this.schedulesIds.add(schedule.getId()));
		entity.getSeats().forEach(seat -> this.seatsIds.add(seat.getId()));
	}

	public RoomDTO(Long id, Integer number, Long cinemaId) {
		super();
		this.id = id;
		this.number = number;
		this.cinemaId = cinemaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public List<Long> getSchedulesIds() {
		return schedulesIds;
	}

	public List<Long> getSeatsIds() {
		return seatsIds;
	}
}
