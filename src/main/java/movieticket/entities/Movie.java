package movieticket.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer year;
	private String description;
	private Integer duration;
	private Gender gender;
	private Cinema cinema;
	
	private List<Actor> actors = new ArrayList<>();
	private List<Director> directors = new ArrayList<>();
	private List<Schedule> schedules = new ArrayList<>();
	private List<Ticket> tickets = new ArrayList<>();
	
	public Movie() {}

	public Movie(Long id, String name, Integer year, String description, Integer duration, Gender gender, Cinema cinema) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.description = description;
		this.duration = duration;
		this.gender = gender;
		this.cinema = cinema;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public List<Director> getDirectors() {
		return directors;
	}

	public List<Schedule> getSchedules() {
		return schedules;
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
		Movie other = (Movie) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
