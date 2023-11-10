package movieticket.controllers;

import java.util.List;

import movieticket.dtos.GenderDTO;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.GenderService;

public class GenderController {

	private GenderService service = new GenderService();
	
	public void findAll() {
		List<GenderDTO> list = service.findAll();
		for(GenderDTO genderDto : list) {
			System.out.println(genderDto);
		}
	}
	
	public void findById(Long id) {
		try {
			GenderDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(GenderDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException | DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Long id, GenderDTO dto) {
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
