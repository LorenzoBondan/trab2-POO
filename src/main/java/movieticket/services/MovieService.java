package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.MovieDTO;
import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.entities.Movie;
import movieticket.entities.Person;
import movieticket.entities.Schedule;
import movieticket.entities.Ticket;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.CinemaRepository;
import movieticket.repositories.GenderRepository;
import movieticket.repositories.MovieRepository;
import movieticket.repositories.PersonRepository;
import movieticket.repositories.ScheduleRepository;
import movieticket.repositories.TicketRepository;

public class MovieService {

	private MovieRepository repository = new MovieRepository();
	private GenderRepository genderRepository = new GenderRepository();
	private CinemaRepository cinemaRepository = new CinemaRepository();
	private PersonRepository personRepository = new PersonRepository();
	private ScheduleRepository scheduleRepository = new ScheduleRepository();
	private TicketRepository ticketRepository = new TicketRepository();
	
	public List<MovieDTO> findAll(){
		List<Movie> list = repository.findAll();
		return list.stream().map(obj -> new MovieDTO(obj)).collect(Collectors.toList());
	}
	
	public List<MovieDTO> findAllByGenderId(Long genderId) {
		List<Movie> list = repository.findAllByGenderId(genderId);
		return list.stream().map(obj -> new MovieDTO(obj)).collect(Collectors.toList());
	}
	
	public MovieDTO findById(Long id) {
		Movie entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		// adicionar lista de atores, diretores, schedules e tickets
		List<Actor> actors = personRepository.findAllActorsByMovieId(id);
		entity.setActors(actors);
		List<Director> directors = personRepository.findAllDirectorsByMovieId(id);
		entity.setDirectors(directors);
		return new MovieDTO(entity);
	}
	
	public void insert(MovieDTO dto) {
		try {
			Movie entity = new Movie();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Filme inserido com sucesso: " + dto);
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, MovieDTO dto) {
		try {
			Movie entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Filme atualizado com sucesso: " + dto);
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		MovieDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Filme deletado com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(MovieDTO dto, Movie entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setYear(dto.getYear());
		entity.setDescription(dto.getDescription());
		entity.setDuration(dto.getDuration());
		entity.setGender(genderRepository.findById(dto.getGenderId()).get());
		entity.setCinema(cinemaRepository.findById(dto.getCinemaId()).get());
		
		entity.getActors().clear();
		for(Long actorId : dto.getActorsIds()) {
			Person actor = personRepository.findById(actorId).get();
			entity.getActors().add((Actor) actor);
		}
		
		entity.getDirectors().clear();
		for(Long directorId : dto.getDirectorsIds()) {
			Person director = personRepository.findById(directorId).get();
			entity.getDirectors().add((Director) director);
		}

		entity.getSchedules().clear();
		for(Long scheduleId : dto.getSchedulesIds()) {
			Schedule schedule = scheduleRepository.findById(scheduleId).get();
			entity.getSchedules().add(schedule);
		}
		
		entity.getTickets().clear();
		for(Long ticketId : dto.getTicketsIds()) {
			Ticket ticket = ticketRepository.findById(ticketId).get();
			entity.getTickets().add(ticket);
		}
	}
}
