package movieticket.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {

	private Long id;
	private Integer number;
	private Cinema cinema;
	
	private List<Schedule> schedules = new ArrayList<>();
	private List<Seat> seats = new ArrayList<>();
	
	public Room() {}

	public Room(Long id, Integer number, Cinema cinema) {
		super();
		this.id = id;
		this.number = number;
		this.cinema = cinema;
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

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public List<Seat> getSeats() {
		return seats;
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
		Room other = (Room) obj;
		return Objects.equals(id, other.id);
	}
}
