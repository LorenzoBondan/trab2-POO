package movieticket.main;

import java.util.List;
import java.util.Optional;

import movieticket.entities.Gender;
import movieticket.services.GenderService;
import movieticket.services.exceptions.ResourceNotFoundException;

public class Menu {

	public static void main(String[] args) {
		GenderService service = new GenderService();
		Gender gender = new Gender(1L, "Action");
		//service.insert(gender);
		
		try {
			Gender updatedGender = new Gender(1L, "Comedy");
			service.update(updatedGender);
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}

		List<Gender> list = service.findAll();
		
		for(Gender g : list) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName());
		}
		
		try {
			Optional<Gender> ge = service.findById(5L);
			System.out.println("GENDER: " + ge.get().getName());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			service.delete(4L);
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
