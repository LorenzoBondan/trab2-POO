package movieticket.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Seat {

	private Long id;
	private Integer number;
	private Integer line;
	private Room room;
	
	private List<Ticket> tickets = new ArrayList<>();
	
	public Seat() {}

	public Seat(Long id, Integer number, Integer line, Room room) {
		super();
		this.id = id;
		this.number = number;
		this.line = line;
		this.room = room;
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

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Ticket> getTickets() {
		return tickets;
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
		Seat other = (Seat) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
