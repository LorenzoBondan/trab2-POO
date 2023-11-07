package movieticket.controllers;

import java.util.List;

import movieticket.dtos.TicketDTO;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.TicketService;

public class TicketController {

	private TicketService service = new TicketService();
	
	public void findAll() {
		List<TicketDTO> list = service.findAll();
        for(TicketDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			TicketDTO dto = service.findById(id);
			System.out.println(dto);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(TicketDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException | DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Long id, TicketDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException | InvalidDataException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
