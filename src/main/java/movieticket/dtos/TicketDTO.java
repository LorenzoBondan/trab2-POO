package movieticket.dtos;

import java.util.Date;

import movieticket.entities.Ticket;

public class TicketDTO {

	private Long id;
	private String clientName;
	private Date date;
	private String phoneNumber;
	private Double price;
	private Boolean halfPrice;
	private Long seatId;
	private Long scheduleId;
	private Long movieId;
	
	public TicketDTO() {}
	
	public TicketDTO(Ticket entity) {
		this.id = entity.getId();
		this.clientName = entity.getClientName();
		this.date = entity.getDate();
		this.phoneNumber = entity.getPhoneNumber();
		this.price = entity.getPrice();
		this.halfPrice = entity.getHalfPrice();
		this.seatId = entity.getSeat().getId();
		this.scheduleId = entity.getSchedule().getId();
		this.movieId = entity.getMovie().getId();
	}

	public TicketDTO(Long id, String clientName, Date date, String phoneNumber, Double price, Boolean halfPrice,
			Long seatId, Long scheduleId, Long movieId) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.date = date;
		this.phoneNumber = phoneNumber;
		this.price = price;
		this.halfPrice = halfPrice;
		this.seatId = seatId;
		this.scheduleId = scheduleId;
		this.movieId = movieId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getHalfPrice() {
		return halfPrice;
	}

	public void setHalfPrice(Boolean halfPrice) {
		this.halfPrice = halfPrice;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	@Override
	public String toString() {
		return "TicketDTO [id=" + id + ", clientName=" + clientName + ", date=" + date + ", phoneNumber=" + phoneNumber
				+ ", price=" + price + ", halfPrice=" + halfPrice + ", seatId=" + seatId + ", scheduleId=" + scheduleId
				+ ", movieId=" + movieId + "]";
	}
}
