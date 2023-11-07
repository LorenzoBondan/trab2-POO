package movieticket.controllers;

import java.util.Date;
import java.util.List;

import movieticket.dtos.MovieDTO;
import movieticket.exceptions.*;
import movieticket.services.MovieService;

public class MovieController {

	private MovieService service = new MovieService();
	
	public void findAll() {
		List<MovieDTO> list = service.findAll();
        for(MovieDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}

	public void findAllByName(String name) {
		List<MovieDTO> list = service.findAllByName(name);
		if(!list.isEmpty()){
			for(MovieDTO movieDto : list) {
				System.out.println(movieDto.toStringSimple());
			}
		} else{
			System.out.println("Não foram encontrados filmes com este nome.");
		}
	}

	public void findAllByDateRange(Date startDate, Date finalDate) {
		try{
			List<MovieDTO> list = service.findAllByDateRange(startDate, finalDate);
			if(!list.isEmpty()){
				for(MovieDTO movieDto : list) {
					System.out.println(movieDto);
				}
			} else{
				System.out.println("Não foram encontrados filmes para esse período de datas.");
			}
		} catch (InvalidDateRangeException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void findById(Long id) {
		try {
			MovieDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(MovieDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException | DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Long id, MovieDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException | InvalidDataException e) {
			System.out.println(e.getMessage());
		}
	}

	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException | IntegrityViolationException e) {
			System.out.println(e.getMessage());
		}
    }
}
