package movieticket.controllers;

import java.util.List;

import movieticket.dtos.SeatDTO;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.SeatService;

public class SeatController {

	private SeatService service = new SeatService();
	
	public void findAll() {
		List<SeatDTO> list = service.findAll();
        for(SeatDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			SeatDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println("Assento não encontrado");
		}
	}
	
	public void insert(SeatDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, SeatDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Assento não encontrado");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Assento não encontrado");
		}
	}
}
