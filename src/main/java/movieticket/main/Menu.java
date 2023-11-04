package movieticket.main;

import java.util.List;

import movieticket.dtos.ActorDTO;
import movieticket.dtos.GenderDTO;
import movieticket.dtos.MovieDTO;
import movieticket.dtos.PersonDTO;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.MovieService;
import movieticket.services.PersonService;

public class Menu {

	public static void main(String[] args) {
		
		/*
		
		// #### GENDER
		
		GenderService service = new GenderService();
		
		System.out.println("\nGENDER\n");
		
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
		
		
		
		/*
		// #### PERSON
		
		PersonService personService = new PersonService();
		
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
		*/
		
		// #### MOVIE
		
		MovieService movieService = new MovieService();
		
		System.out.println("\nMOVIES\n");
		
		// FINDALL -------------------
		List<MovieDTO> list = movieService.findAll();
		for(MovieDTO g : list) {
			System.out.println(g);
		}
		
		// FINDBYID -------------------
		try {
			MovieDTO ge = movieService.findById(2L);
			System.out.println(ge);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// INSERT ---------------------
		try {
			MovieDTO movie = new MovieDTO(2L, "Saw", 2010, "Saw description", 120, 1L, 1L);
			movieService.insert(movie);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		// UPDATE --------------------
		try {
			MovieDTO updatedMovie = new MovieDTO(1L, "Scary Movie 2", 2002, "A parody of Horror movies", 110, 1L, 1L);
			movieService.update(1L, updatedMovie);
			System.out.println("Updated successfully: " + updatedMovie.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			movieService.delete(4L);
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
