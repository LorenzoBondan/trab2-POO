package movieticket.controllers;

import java.util.List;

import movieticket.dtos.MovieDTO;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.MovieService;

public class MovieController {

	private MovieService service = new MovieService();
	
	public void findAll() {
		List<MovieDTO> list = service.findAll();
        for(MovieDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			MovieDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println("Filme não encontrado");
		}
	}
	
	public void insert(MovieDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, MovieDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Filme não encontrado");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Filme não encontrado");
		}
	}
}
