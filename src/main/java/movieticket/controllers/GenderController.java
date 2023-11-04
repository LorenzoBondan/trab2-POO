package movieticket.controllers;

import java.util.List;

import movieticket.dtos.GenderDTO;
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
			System.out.println("Gênero não encontrado");
		}
	}
	
	public void insert(GenderDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, GenderDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Gênero não encontrado");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Gênero não encontrado");
		}
	}
}
