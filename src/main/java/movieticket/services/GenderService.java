package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.GenderDTO;
import movieticket.entities.Gender;
import movieticket.entities.Movie;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.GenderRepository;
import movieticket.repositories.MovieRepository;

public class GenderService {

	private GenderRepository repository = new GenderRepository();
	private MovieRepository movieRepository = new MovieRepository();
	
	public List<GenderDTO> findAll(){
		List<Gender> list = repository.findAll();
		return list.stream().map(obj -> new GenderDTO(obj)).collect(Collectors.toList());
	}
	
	public GenderDTO findById(Long id) {
		Gender entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new GenderDTO(entity);
	}
	
	public void insert(GenderDTO dto) {
		Gender entity = new Gender();
		copyDtoToEntity(dto, entity);
		repository.insert(entity);
	}
	
	public void update(Long id, GenderDTO dto) {
		Gender entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		copyDtoToEntity(dto, entity);
		repository.update(entity);
	}
	
	public void delete(Long id) {
		repository.delete(id);
	}
	
	private void copyDtoToEntity(GenderDTO dto, Gender entity) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		
		entity.getMovies().clear();
		for(Long movieId : dto.getMoviesIds()) {
			Movie movie = movieRepository.findById(movieId).get();
			entity.getMovies().add(movie);
		}
	}
}
