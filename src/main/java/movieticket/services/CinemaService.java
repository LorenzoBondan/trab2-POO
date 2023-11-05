package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.CinemaDTO;
import movieticket.entities.Cinema;
import movieticket.entities.Movie;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.CinemaRepository;
import movieticket.repositories.MovieRepository;

public class CinemaService {

	private CinemaRepository repository = new CinemaRepository();
	private MovieRepository movieRepository = new MovieRepository();
	
	public List<CinemaDTO> findAll(){
		List<Cinema> list = repository.findAll();
		return list.stream().map(obj -> new CinemaDTO(obj)).collect(Collectors.toList());
	}
	
	public CinemaDTO findById(Long id) {
		Cinema entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new CinemaDTO(entity);
	}
	
	public void insert(CinemaDTO dto) {
		try {
			Cinema entity = new Cinema();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Cinema inserido com sucesso: " + dto);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, CinemaDTO dto) {
		try {
			Cinema entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Cinema atualizado com sucesso: " + dto);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		CinemaDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Cinema deletado com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(CinemaDTO dto, Cinema entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());
		
		entity.getMovies().clear();
		for(Long movieId : dto.getMoviesIds()) {
			Movie movie = movieRepository.findById(movieId).get();
			entity.getMovies().add(movie);
		}
	}
}
