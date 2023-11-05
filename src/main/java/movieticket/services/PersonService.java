package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.ActorDTO;
import movieticket.dtos.DirectorDTO;
import movieticket.dtos.PersonDTO;
import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.entities.Movie;
import movieticket.entities.Person;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.MovieRepository;
import movieticket.repositories.PersonRepository;

public class PersonService {

	private PersonRepository repository = new PersonRepository();
	private MovieRepository movieRepository = new MovieRepository();
	
	public List<PersonDTO> findAll(){
		List<Person> list = repository.findAll();
		return list.stream().map(obj -> new PersonDTO(obj)).collect(Collectors.toList());
	}
	
	public List<ActorDTO> findAllActors(){
		List<Actor> list = repository.findAllActors();
		return list.stream().map(obj -> new ActorDTO(obj)).collect(Collectors.toList());
	}
	
	public List<DirectorDTO> findAllDirectors(){
		List<Director> list = repository.findAllDirectors();
		return list.stream().map(obj -> new DirectorDTO(obj)).collect(Collectors.toList());
	}
	
	public List<ActorDTO> findAllActorsByMovieId(Long movieId){
		List<Actor> list = repository.findAllActorsByMovieId(movieId);
		return list.stream().map(obj -> new ActorDTO(obj)).collect(Collectors.toList());
	}
	
	public List<DirectorDTO> findAllDirectorsByMovieId(Long movieId){
		List<Director> list = repository.findAllDirectorsByMovieId(movieId);
		return list.stream().map(obj -> new DirectorDTO(obj)).collect(Collectors.toList());
	}
	
	public PersonDTO findById(Long id) {
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new PersonDTO(entity);
	}
	
	public ActorDTO findActorById(Long id) {
		Actor entity = repository.findActorById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		List<Movie> list = movieRepository.findAllByActorId(id);
		entity.setMovies(list);
		return new ActorDTO(entity);
	}
	
	public DirectorDTO findDirectorById(Long id) {
		Director entity = repository.findDirectorById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		List<Movie> list = movieRepository.findAllByDirectorId(id);
		entity.setMovies(list);
		return new DirectorDTO(entity);
	}
	
	public void insert(PersonDTO dto) {
		try{
			Person entity = new Person();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Pessoa inserida com sucesso: " + dto);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, PersonDTO dto) {
		try {
			Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Pessoa atualizada com sucesso: " + dto);
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		PersonDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Pessoa deletada com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(PersonDTO dto, Person entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setRole(dto.getRole());
		if(dto.getMarriedId() != null) {
			entity.setMarried(repository.findById(dto.getMarriedId()).get());
		}
	}
}
