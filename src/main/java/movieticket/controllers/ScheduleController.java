package movieticket.controllers;

import java.util.List;

import movieticket.dtos.ScheduleDTO;
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
			System.out.println(dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Horário não encontrado");
		}
	}
	
	public void insert(ScheduleDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, ScheduleDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Horário não encontrado");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Horário não encontrado");
		}
	}
}
