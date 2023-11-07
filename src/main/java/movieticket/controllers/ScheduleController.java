package movieticket.controllers;

import java.util.ArrayList;
import java.util.List;

import movieticket.dtos.ScheduleDTO;
import movieticket.entities.Seat;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.ScheduleService;

public class ScheduleController {

	private ScheduleService service = new ScheduleService();
	
	public void findAll() {
		List<ScheduleDTO> list = service.findAll();
        for(ScheduleDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			ScheduleDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void findByMovieId(Long id) {
		try {
			ScheduleDTO dto = service.findById(id);
			System.out.println(dto.toStringWithList());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	public List<Seat> checkAvailableSeats(Long id, Long movie_id) {
		try {
			return service.checkAvailableSeats(id, movie_id);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	
	public void insert(ScheduleDTO dto) {
		try {
			service.insert(dto);
		} catch(InvalidDataException | DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Long id, ScheduleDTO dto) {
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
