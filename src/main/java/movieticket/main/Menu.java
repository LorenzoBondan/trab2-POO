package movieticket.main;

import java.util.List;
import java.util.Optional;

import movieticket.dtos.GenderDTO;
import movieticket.entities.Actor;
import movieticket.entities.Gender;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.PersonService;
import movieticket.services.GenderService;

public class Menu {

	public static void main(String[] args) {
		GenderService service = new GenderService();
		PersonService actorService = new PersonService();
		
		// FINDALL -------------------
		List<GenderDTO> list = service.findAll();
		for(GenderDTO g : list) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName());
		}
		
		/*
		// FINDBYID -------------------
		try {
			Optional<Gender> ge = service.findById(5L);
			System.out.println("GENDER: " + ge.get().getName());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// INSERT ---------------------
		try {
			Gender gender = new Gender(1L, "Action");
			service.insert(gender);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		// UPDATE --------------------
		try {
			Gender updatedGender = new Gender(1L, "Comedy");
			service.update(updatedGender);
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
		
		
		// ############
		System.out.println("\nACTOR\n");
		
		// INSERT ---------------------
		try {
			Actor lorenzo = (Actor) actorService.findById(1L).get();
			Actor actor = new Actor(2L, "Alisson", lorenzo);
			actorService.insert(actor);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		/*
		// FINDALL -------------------
		List<Actor> actorList = actorService.findAll();
		for(Actor g : actorList) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName() + "Married: " + g.getMarried());
		}
		*/
		
	}
}
