package movieticket.dtos;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import movieticket.entities.Schedule;

public class ScheduleDTO {

	private Long id;
	private Date date;
	private LocalTime time;
	private Long roomId;
	private Long movieId;
	
	private List<Long> ticketsIds = new ArrayList<>();
	
	public ScheduleDTO() {}
	
	public ScheduleDTO(Schedule entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.time = entity.getTime();
		this.roomId = entity.getRoom().getId();
		this.movieId = entity.getMovie().getId();
		
		entity.getTickets().forEach(ticket -> this.ticketsIds.add(ticket.getId()));
	}

	public ScheduleDTO(Long id, Date date, LocalTime time, Long roomId, Long movieId) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.roomId = roomId;
		this.movieId = movieId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public List<Long> getTicketsIds() {
		return ticketsIds;
	}

	@Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);
        return "ScheduleDTO [id=" + id + ", date=" + formattedDate + ", time=" + time + ", roomId=" + roomId + ", movieId="
                + movieId + "]";
    }
}
