package movieticket.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import movieticket.controllers.GenderController;
import movieticket.dtos.ActorDTO;
import movieticket.dtos.GenderDTO;
import movieticket.dtos.MovieDTO;
import movieticket.dtos.PersonDTO;
import movieticket.dtos.ScheduleDTO;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.services.GenderService;
import movieticket.services.MovieService;
import movieticket.services.PersonService;
import movieticket.services.ScheduleService;
import movieticket.util.Util;

public class Menu {
	
	GenderService genderService = new GenderService();
	
	public static void showMenu() {
		Scanner in = new Scanner(System.in);
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Movie Ticket Cinemas");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha um dos tópicos a seguir:");
			System.out.println("1) Gêneros");
			System.out.println("2) Filmes");
			System.out.println("3) Salas");
			System.out.println("4) Horários");
			System.out.println("5) Assentos");
			System.out.println("6) Ingressos");
			System.out.println("7) Atores");
			System.out.println("8) Diretores");
			System.out.println("9) Cinemas");
			System.out.println("10) Sair do sistema");
			System.out.println("------------------------------------------------------------");
			
			opc = Util.readInt("");

			switch (opc) {
            case 1:
                System.out.println("Opção 1 selecionada: Gêneros");
                showGenders();
                break;
            case 2:
                System.out.println("Opção 2 selecionada: Filmes");
                break;
            case 3:
                System.out.println("Opção 3 selecionada: Salas");
                break;
            case 4:
                System.out.println("Opção 4 selecionada: Horários");
                break;
            case 5:
                System.out.println("Opção 5 selecionada: Assentos");
                break;
            case 6:
                System.out.println("Opção 6 selecionada: Ingressos");
                break;
            case 7:
                System.out.println("Opção 7 selecionada: Atores");
                break;
            case 8:
                System.out.println("Opção 8 selecionada: Diretores");
                break;
            case 9:
                System.out.println("Opção 9 selecionada: Cinemas");
                break;
            case 10:
                System.out.println("Saindo do sistema...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opc != 10);
		in.close();
	}
	
	public static void showGenders() {
		GenderController genderController = new GenderController();
		Scanner in = new Scanner(System.in);
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Gêneros");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os gêneros");
			System.out.println("2) Mostrar um gênero por código");
			System.out.println("3) Inserir novo gênero");
			System.out.println("4) Atualizar um gênero");
			System.out.println("5) Excluir um gênero");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os gêneros\n");
	            genderController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um gênero por código\n");
	            genderController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            genderController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo gênero\n");
	            GenderDTO newDto = new GenderDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setName(Util.readString("Digite o nome: "));
	            genderController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um gênero\n");
	            genderController.findAll();
	            GenderDTO updatedDto = new GenderDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setName(Util.readString("Digite o nome: "));
	            genderController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um gênero\n");
	            genderController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            genderController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Gêneros...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
		in.close();
	}

	public static void main(String[] args) throws ParseException {
		
		showMenu();
		/*
		// #### GENDER
		GenderService genderService = new GenderService();
		
		
		System.out.println("\nGENDER\n");
		
		
		
		// FINDALL -------------------
		List<GenderDTO> list = genderService.findAll();
		for(GenderDTO g : list) {
			System.out.println("Id: " + g.getId() + " Name: " + g.getName());
		}
		
		// FINDBYID -------------------
		try {
			GenderDTO ge = genderService.findById(5L);
			System.out.println("GENDER: " + ge.getName());
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// INSERT ---------------------
		try {
			GenderDTO gender = new GenderDTO(1L, "Action");
			genderService.insert(gender);
		} catch(DuplicateResourceException e) {
			System.out.println(e.getMessage());
		}
		
		// UPDATE --------------------
		try {
			GenderDTO updatedGender = new GenderDTO(1L, "Comedy");
			genderService.update(1L, updatedGender);
			System.out.println("Updated successfully: " + updatedGender.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			genderService.delete(4L);
			System.out.println("Gender deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// #### PERSON
		
		PersonService personService = new PersonService();
		
		System.out.println("\nACTOR\n");
		
		// INSERT ---------------------
		try {
			ActorDTO actor = new ActorDTO(3L, "Marcos", "Actor", 1L);
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
			PersonDTO updatedActor = new ActorDTO(1L, "Lorenzo Bondan", "Director", 2L);
			personService.update(1L, updatedActor);
			System.out.println("Updated successfully: " + updatedActor.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			personService.delete(3L);
			System.out.println("Person deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
		// #### MOVIE
		
		MovieService movieService = new MovieService();
		
		System.out.println("\nMOVIES\n");
		
		// FINDALL -------------------
		List<MovieDTO> listMovie = movieService.findAll();
		for(MovieDTO g : listMovie) {
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
			System.out.println("Movie deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
		// #### SCHEDULE
		
		ScheduleService scheduleService = new ScheduleService();
		
		System.out.println("\nSCHEDULES\n");
		
		// FINDALL -------------------
		List<ScheduleDTO> listSchedule = scheduleService.findAll();
		for(ScheduleDTO g : listSchedule) {
			System.out.println(g);
		}
		
		// FINDBYID -------------------
		try {
			ScheduleDTO ge = scheduleService.findById(2L);
			System.out.println(ge);
		} catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// INSERT ---------------------
		try {
		    SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date date;
		    try {
		        date = inputDateFormat.parse("10/05/2023");
		    } catch (ParseException e) {
		        e.printStackTrace();
		        date = null;
		    }

		    LocalTime time = LocalTime.parse("18:00:00");
		    ScheduleDTO schedule = new ScheduleDTO(5L, date, time, 1L, 1L);
		    scheduleService.insert(schedule);
		} catch (DuplicateResourceException e) {
		    System.out.println(e.getMessage());
		}
		
		// UPDATE --------------------
		try {
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date date;
		    try {
		        date = inputDateFormat.parse("10/05/2023");
		    } catch (ParseException e) {
		        e.printStackTrace();
		        date = null;
		    }
		    LocalTime time = LocalTime.parse("13:00:00");
			ScheduleDTO updatedSchedule = new ScheduleDTO(1L, date, time, 1L, 1L);
			scheduleService.update(1L, updatedSchedule);
			System.out.println("Updated successfully: " + updatedSchedule.toString());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// DELETE ---------------------
		try {
			scheduleService.delete(5L);
			System.out.println("Schedule deleted successfully.");
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		*/
	}
	
}
