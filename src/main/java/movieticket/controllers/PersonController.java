package movieticket.controllers;

import java.util.List;

import movieticket.dtos.PersonDTO;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.PersonService;

public class PersonController {

	private PersonService service = new PersonService();
	
	public void findAll() {
		List<PersonDTO> list = service.findAll();
        for(PersonDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	public void findById(Long id) {
		try {
			PersonDTO dto = service.findById(id);
			System.out.println(dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		}
	}
	
	public void insert(PersonDTO dto) {
		service.insert(dto);
	}
	
	public void update(Long id, PersonDTO dto) {
		try {
			service.update(id, dto);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		}
	}
	
	public void delete(Long id) {
		try {
			service.delete(id);
		} catch(ResourceNotFoundException e) {
			System.out.println("Pessoa não encontrada");
		}
	}
}
