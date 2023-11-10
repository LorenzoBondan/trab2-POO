package movieticket.dtos;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Movie;

public class MovieDTO {

	private Long id;
	private String name;
	private Integer year;
	private String description;
	private Integer duration;
	private Long genderId;
	private Long cinemaId;
	
	private List<Long> actorsIds = new ArrayList<>();
	private List<Long> directorsIds = new ArrayList<>();
	private List<Long> schedulesIds = new ArrayList<>();
	private List<Long> ticketsIds = new ArrayList<>();
	
	public MovieDTO() {}
	
	public MovieDTO(Movie entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.year = entity.getYear();
		this.description = entity.getDescription();
		this.duration = entity.getDuration();
		this.genderId = entity.getGender().getId();
		this.cinemaId = entity.getCinema().getId();
		
		entity.getActors().forEach(actor -> this.actorsIds.add(actor.getId()));
		entity.getDirectors().forEach(director -> this.directorsIds.add(director.getId()));
		entity.getSchedules().forEach(schedule -> this.schedulesIds.add(schedule.getId()));
		entity.getTickets().forEach(ticket -> this.ticketsIds.add(ticket.getId()));
	}

	public MovieDTO(Long id, String name, Integer year, String description, Integer duration, Long genderId,
			Long cinemaId) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.description = description;
		this.duration = duration;
		this.genderId = genderId;
		this.cinemaId = cinemaId;
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

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public List<Long> getActorsIds() {
		return actorsIds;
	}

	public List<Long> getDirectorsIds() {
		return directorsIds;
	}

	public List<Long> getSchedulesIds() {
		return schedulesIds;
	}

	public List<Long> getTicketsIds() {
		return ticketsIds;
	}

	public void setActorsIds(List<Long> actorsIds) {
		this.actorsIds = actorsIds;
	}

	public void setDirectorsIds(List<Long> directorsIds) {
		this.directorsIds = directorsIds;
	}

	public void setSchedulesIds(List<Long> schedulesIds) {
		this.schedulesIds = schedulesIds;
	}

	public void setTicketsIds(List<Long> ticketsIds) {
		this.ticketsIds = ticketsIds;
	}

	@Override
	public String toString() {
		return "Filme [código=" + id + ", nome=" + name + ", ano=" + year + ", descrição=" + description
				+ ", duração=" + duration + ", Código do gênero=" + genderId + ", Código do cinema=" + cinemaId + "]";
	}

	public String toStringWithList() {
		return "Filme [código=" + id + ", nome=" + name + ", ano=" + year + ", descrição=" + description
				+ ", duração=" + duration + ", código do gênero=" + genderId + ", código do cinema=" + cinemaId + ", códigos dos atores="
				+ actorsIds + ", códigos dos diretores=" + directorsIds + ", códigos dos horários=" + schedulesIds + ", códigos dos ingressos="
				+ ticketsIds + "]";
	}

	public String toStringSimple(){
		return "Filme [código=" + id + ", nome=" + name + "]";
	}
}
