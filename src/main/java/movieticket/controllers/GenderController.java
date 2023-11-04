package movieticket.controllers;

import java.util.List;

import movieticket.dtos.GenderDTO;
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
		GenderDTO dto = service.findById(id);
		System.out.println(dto.toStringWithList());
	}
	
	public void insert(GenderDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, GenderDTO dto) {
		service.update(id, dto);
	}
	
	public void delete(Long id) {
		service.delete(id);
	}
}
