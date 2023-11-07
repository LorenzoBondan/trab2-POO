package movieticket.services;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import movieticket.dtos.MovieDTO;
import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.entities.Movie;
import movieticket.entities.Person;
import movieticket.entities.Schedule;
import movieticket.entities.Ticket;
import movieticket.exceptions.*;
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

	public List<MovieDTO> findAllByName(String name){
		List<Movie> list = repository.findAllByName(name);
		return list.stream().map(obj -> new MovieDTO(obj)).collect(Collectors.toList());
	}
	
	public List<MovieDTO> findAllByGenderId(Long genderId) {
		List<Movie> list = repository.findAllByGenderId(genderId);
		return list.stream().map(obj -> new MovieDTO(obj)).collect(Collectors.toList());
	}

	public List<MovieDTO> findAllByDateRange(Date startDate, Date finalDate){
		if (startDate.after(finalDate)) {
			throw new InvalidDateRangeException("Data inicial não pode ser maior do que a data final.");
		}

		List<Movie> movies = repository.findAll();
		List<Movie> newMovies = new ArrayList<>();

		for (Movie movie : movies) {
			List<Schedule> schedules = scheduleRepository.findAllByMovieId(movie.getId());
			for (Schedule schedule : schedules) {
				if (!schedule.getDate().before(startDate) && !schedule.getDate().after(finalDate)) {
					newMovies.add(movie);
					List<Ticket> tickets = ticketRepository.findAllByMovieId(movie.getId());
					movie.setTickets(tickets);
					break;
				}
			}
		}

		Set<Movie> nonReplicatedMovies = new HashSet<>(newMovies);

		List<Movie> finalList = new ArrayList<>(nonReplicatedMovies);
		sortByTicketsSold(finalList);
		return finalList.stream().map(obj -> new MovieDTO(obj)).collect(Collectors.toList());
	}

	private void sortByTicketsSold(List<Movie> movies) {
		Comparator<Movie> ticketsSoldComparator = Comparator.comparingInt(movie -> movie.getTickets().size());
		Collections.sort(movies, ticketsSoldComparator.reversed());
	}
	
	public MovieDTO findById(Long id) {
		Movie entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com o ID: " + id));
		List<Actor> actors = personRepository.findAllActorsByMovieId(id);
		entity.setActors(actors);
		List<Director> directors = personRepository.findAllDirectorsByMovieId(id);
		entity.setDirectors(directors);
		List<Schedule> schedules = scheduleRepository.findAllByMovieId(id);
		entity.setSchedules(schedules);
		/*List<Ticket> tickets = ticketRepository.findAllByMovieId(id);
		entity.setTickets(tickets);*/
		return new MovieDTO(entity);
	}
	
	public void insert(MovieDTO dto) {
		try {
			Movie entity = new Movie();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Filme inserido com sucesso: " + dto);
		} catch (DuplicateResourceException e) {
			throw new DuplicateResourceException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, MovieDTO dto) {
		try {
			Movie entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com o ID: " + id));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Filme atualizado com sucesso: " + dto);
		} catch (ResourceNotFoundException ex){
			throw new ResourceNotFoundException("Filme não encontrado com o ID: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		MovieDTO dto = findById(id);
		if(dto != null) {
			if(dto.getSchedulesIds().isEmpty() && dto.getTicketsIds().isEmpty()){
				repository.delete(id);
				System.out.println("Filme deletado com sucesso: " + id);
			} else{
				throw new IntegrityViolationException("Não é possível deletar pois há dependências relacionadas a esse objeto");
			}
		} else{
			throw new ResourceNotFoundException("Filme não encontrado com o ID: " + id);
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
			entity.getActors().add(new Actor(actor.getId(), actor.getName(), actor.getRole(), actor.getMarried())); 
		}
		
		entity.getDirectors().clear();
		for(Long directorId : dto.getDirectorsIds()) {
			Person director = personRepository.findById(directorId).get();
			entity.getDirectors().add(new Director(director.getId(), director.getName(), director.getRole(), director.getMarried())); 
		}
		
		entity.getSchedules().clear();
		for(Long scheduleId : dto.getSchedulesIds()) {
			Schedule schedule = scheduleRepository.findById(scheduleId).get();
			entity.getSchedules().add(schedule);
		}
		
		/*entity.getTickets().clear();
		for(Long ticketId : dto.getTicketsIds()) {
			Ticket ticket = ticketRepository.findById(ticketId).get();
			entity.getTickets().add(ticket);
		}*/
	}
}
