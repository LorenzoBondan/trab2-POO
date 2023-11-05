package movieticket.controllers;

import java.util.List;

import movieticket.dtos.RoomDTO;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.RoomService;

public class RoomController {

	private RoomService service = new RoomService();
	
	public void findAll() {
		List<RoomDTO> list = service.findAll();
        for(RoomDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			RoomDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println("Sala não encontrada");
		}
	}
	
	public void insert(RoomDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, RoomDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Sala não encontrada");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Sala não encontrada");
		}
	}
}
