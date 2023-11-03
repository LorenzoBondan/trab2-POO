package movieticket.entities;

import java.util.Date;
import java.util.Objects;

public class Ticket {

	private Long id;
	private String clientName;
	private Date date;
	private String phoneNumber;
	private Double price;
	private Boolean halfPrice;
	private Seat seat;
	private Schedule schedule;
	private Movie movie;
	
	public Ticket() {}

	public Ticket(Long id, String clientName, Date date, String phoneNumber, Double price, Boolean halfPrice, Seat seat,
			Schedule schedule, Movie movie) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.date = date;
		this.phoneNumber = phoneNumber;
		this.price = price;
		this.halfPrice = halfPrice;
		this.seat = seat;
		this.schedule = schedule;
		this.movie = movie;
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

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
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
		Ticket other = (Ticket) obj;
		return Objects.equals(id, other.id);
	}
}
