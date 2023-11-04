package movieticket.controllers;

import java.util.List;

import movieticket.dtos.GenderDTO;
import movieticket.services.GenderService;

public class GenderController {

	private GenderService service = new GenderService();
	
	public void findAll() {
		List<GenderDTO> list = service.findAll();
        for(GenderDTO genderDto : list) {
        	System.out.println(genderDto);
        }
	}
	
	
}
