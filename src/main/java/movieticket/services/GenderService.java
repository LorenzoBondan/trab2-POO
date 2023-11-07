package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.GenderDTO;
import movieticket.entities.Gender;
import movieticket.entities.Movie;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
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
		Gender entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gênero não encontrado com o ID: " + id));
		List<Movie> list = movieRepository.findAllByGenderId(id); // adicionar seus respectivos filmes
		entity.setMovies(list);
		return new GenderDTO(entity);
	}
	
	public void insert(GenderDTO dto) {
		try {
			Gender entity = new Gender();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Gênero inserido com sucesso: " + dto);
		} catch (DuplicateResourceException e) {
			throw new DuplicateResourceException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, GenderDTO dto) {
		try {
			Gender entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gênero não encontrado com o ID: " + id));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Gênero atualizado com sucesso: " + dto);
		} catch (ResourceNotFoundException ex){
			throw new ResourceNotFoundException("Gênero não encontrado com o ID: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		GenderDTO dto = findById(id);
		if(dto != null) {
			if(dto.getMoviesIds().isEmpty()){
				repository.delete(id);
				System.out.println("Gênero deletado com sucesso: " + id);
			} else{
				throw new IntegrityViolationException("Não é possível deletar pois há dependências relacionadas a esse objeto");
			}
		} else{
			throw new ResourceNotFoundException("Gênero não encontrado com o ID:" + id);
		}
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
