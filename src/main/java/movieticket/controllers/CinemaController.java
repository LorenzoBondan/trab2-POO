package movieticket.controllers;

import java.util.List;

import movieticket.dtos.CinemaDTO;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.CinemaService;

public class CinemaController {

	private CinemaService service = new CinemaService();
	
	public void findAll() {
		List<CinemaDTO> list = service.findAll();
        for(CinemaDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			CinemaDTO dto = service.findById(id);
			System.out.println(dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Cinema não encontrado");
		}
	}
	
	public void insert(CinemaDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, CinemaDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Cinema não encontrado");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Cinema não encontrado");
		}
	}
}
