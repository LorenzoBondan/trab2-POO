package movieticket.main;

import java.util.List;

import movieticket.dtos.ActorDTO;
import movieticket.dtos.PersonDTO;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.PersonService;

public class Menu {

	public static void main(String[] args) {
		
		/*
		GenderService service = new GenderService();
		
		// FINDALL -------------------
		List<GenderDTO> list = service.findAll();
		for(GenderDTO g : list) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName());
		}
		
		// FINDBYID -------------------
		try {
			GenderDTO ge = service.findById(5L);
			System.out.println("GENDER: " + ge.getName());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// INSERT ---------------------
		try {
			GenderDTO gender = new GenderDTO(1L, "Action");
			service.insert(gender);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		// UPDATE --------------------
		try {
			GenderDTO updatedGender = new GenderDTO(1L, "Comedy");
			service.update(1L, updatedGender);
			System.out.println("Updated successfully: " + updatedGender.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			service.delete(4L);
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		*/
		
		
		PersonService personService = new PersonService();
		
		// ############
		System.out.println("\nACTOR\n");
		
		// INSERT ---------------------
		try {
			ActorDTO actor = new ActorDTO(3L, "Marcos", 1L);
			personService.insert(actor);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		// FINDALL -------------------
		List<PersonDTO> actorList = personService.findAll();
		for(PersonDTO g : actorList) {
			System.out.println(g);
		}
		
		// UPDATE --------------------
		try {
			PersonDTO updatedActor = new ActorDTO(1L, "Lorenzo Bondan", 2L);
			personService.update(1L, updatedActor);
			System.out.println("Updated successfully: " + updatedActor.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			personService.delete(3L);
			System.out.println("Object deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
