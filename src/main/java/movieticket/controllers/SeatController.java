package movieticket.controllers;

import java.util.List;

import movieticket.dtos.SeatDTO;
import movieticket.entities.Seat;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
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

	public List<SeatDTO> findAvailableSeats(Long id, Long movie_id) {
		try {
			return service.findAvailableSeats(id, movie_id);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void findById(Long id) {
		try {
			SeatDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(SeatDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException | DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Long id, SeatDTO dto) {
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
