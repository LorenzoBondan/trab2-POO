package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Seat;

public class SeatDTO {

	private Long id;
	private Integer number;
	private Integer line;
	private Long roomId;
	
	private List<Long> ticketsIds = new ArrayList<>();
	
	public SeatDTO() {}
	
	public SeatDTO(Seat entity) {
		this.id = entity.getId();
		this.number = entity.getNumber();
		this.line = entity.getLine();
		this.roomId = entity.getRoom().getId();
		
		entity.getTickets().forEach(ticket -> this.ticketsIds.add(ticket.getId()));
	}

	public SeatDTO(Long id, Integer number, Integer line, Long roomId) {
		super();
		this.id = id;
		this.number = number;
		this.line = line;
		this.roomId = roomId;
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public List<Long> getTicketsIds() {
		return ticketsIds;
	}
	
	@Override
	public String toString() {
		return "SeatDTO [id=" + id + ", number=" + number + ", line=" + line + ", roomId=" + roomId + "]";
	}

	public String toStringWithList() {
		return "SeatDTO [id=" + id + ", number=" + number + ", line=" + line + ", roomId=" + roomId + ", ticketsIds="
				+ ticketsIds + "]";
	}
}
