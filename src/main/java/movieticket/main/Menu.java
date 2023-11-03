package movieticket.main;

import java.util.ArrayList;
import java.util.List;

import movieticket.entities.Gender;
import movieticket.services.GenderService;

public class Menu {

	public static void main(String[] args) {
		GenderService service = new GenderService();
		Gender gender = new Gender(1L, "Action");
		//service.insert(gender);
		
		Gender updatedGender = new Gender(1L, "Comedy");
		//service.update(updatedGender);
		
		List<Gender> list = service.findAll();
		
		for(Gender g : list) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName());
		}
		
		//service.delete(4L);
		
	}
}
