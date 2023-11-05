package movieticket.main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import movieticket.controllers.CinemaController;
import movieticket.controllers.GenderController;
import movieticket.controllers.MovieController;
import movieticket.controllers.PersonController;
import movieticket.controllers.RoomController;
import movieticket.controllers.ScheduleController;
import movieticket.controllers.SeatController;
import movieticket.controllers.TicketController;
import movieticket.dtos.CinemaDTO;
import movieticket.dtos.GenderDTO;
import movieticket.dtos.MovieDTO;
import movieticket.dtos.RoomDTO;
import movieticket.dtos.ScheduleDTO;
import movieticket.dtos.SeatDTO;
import movieticket.dtos.TicketDTO;
import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.repositories.PersonRepository;
import movieticket.util.Util;

public class Menu {
	
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
                showMovies();
                break;
            case 3:
                System.out.println("Opção 3 selecionada: Salas");
                showRooms();
                break;
            case 4:
                System.out.println("Opção 4 selecionada: Horários");
                showSchedules();
                break;
            case 5:
                System.out.println("Opção 5 selecionada: Assentos");
                showSeats();
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
                showCinemas();
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
	}
	
	public static void showMovies() {
		MovieController movieController = new MovieController();
		GenderController genderController = new GenderController();
		CinemaController cinemaController = new CinemaController();
		PersonController personController = new PersonController();
		PersonRepository personRepository = new PersonRepository();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Filmes");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os filmes");
			System.out.println("2) Mostrar um filme por código");
			System.out.println("3) Inserir novo filme");
			System.out.println("4) Atualizar um filme");
			System.out.println("5) Excluir um filme");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os filmes\n");
	            movieController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um filme por código\n");
	            movieController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            movieController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo filme\n");
	            MovieDTO newDto = new MovieDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setName(Util.readString("Digite o nome: "));
	            newDto.setDescription(Util.readString("Digite a descrição: "));
	            newDto.setDuration(Util.readInt("Digite a duração em minutos: "));
	            newDto.setYear(Util.readInt("Digite o ano: "));
	            genderController.findAll();
	            newDto.setGenderId(Util.readLong("Digite o código do gênero: "));
	            cinemaController.findAll();
	            newDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
	            
	            personController.findAllDirectors();
	            List<Long> directorsIds = new ArrayList<>();
	            int stopDirector = 1;
	            while(stopDirector != 0) {
	            	Long directorId = Util.readLong("Digite o código do diretor: ");
	            	directorsIds.add(directorId);
	            	personRepository.addDirectorToMovie(directorId, newDto.getId()); // add no csv muitos para muitos
	            	stopDirector = Util.readInt("Deseja adicionar mais algum diretor? 1-Sim 0-Não: ");
	            	if(stopDirector == 0) {
	            		break;
	            	}
	            }
	            newDto.setDirectorsIds(directorsIds);
	            
	            personController.findAllActors();
	            List<Long> actorsIds = new ArrayList<>();
	            int stopActor = 1;
	            while(stopActor != 0) {
	            	Long actorId = Util.readLong("Digite o código do ator: ");
	            	actorsIds.add(actorId);
	            	personRepository.addActorToMovie(actorId, newDto.getId()); // add no csv muitos para muitos
	            	stopActor = Util.readInt("Deseja adicionar mais algum ator? 1-Sim 0-Não: ");
	            	if(stopActor == 0) {
	            		break;
	            	}
	            }
	            newDto.setActorsIds(actorsIds);
	           
	            movieController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um filme\n");
	            movieController.findAll();
	            MovieDTO updatedDto = new MovieDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setName(Util.readString("Digite o nome: "));
	            updatedDto.setDescription(Util.readString("Digite a descrição: "));
	            updatedDto.setDuration(Util.readInt("Digite a duração em minutos: "));
	            updatedDto.setYear(Util.readInt("Digite o ano: "));
	            genderController.findAll();
	            updatedDto.setGenderId(Util.readLong("Digite o código do gênero: "));
	            cinemaController.findAll();
	            updatedDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
	            
	            System.out.println("Diretores já presentes neste filme:");
	            personController.findAllDirectorsByMovieId(updatedDto.getId());
	            int question = Util.readInt("Deseja alterar os diretores desse filme? 1-Sim 0-Não: ");
	            if(question == 1) {
	            	personController.findAllDirectors();
	            	List<Long> updatedDirectorsIds = new ArrayList<>();
		            int updatedStopDirector = 1;
		            while(updatedStopDirector != 0) {
		            	Long directorId = Util.readLong("Digite o código do diretor: ");
		            	updatedDirectorsIds.add(directorId);
		            	personRepository.addDirectorToMovie(directorId, updatedDto.getId()); // add no csv muitos para muitos
		            	stopDirector = Util.readInt("Deseja adicionar mais algum diretor? 1-Sim 0-Não: ");
		            	if(stopDirector == 0) {
		            		break;
		            	}
		            }
		            updatedDto.setDirectorsIds(updatedDirectorsIds);
	            }
	            
	            System.out.println("Atores já presentes nesse filme:");
	            personController.findAllActorsByMovieId(updatedDto.getId());
	            int question2 = Util.readInt("Deseja alterar os atores desse filme? 1-Sim 0-Não: ");
	            if(question2 == 1) {
	            	personController.findAllActors();
	            	List<Long> updatedActorsIds = new ArrayList<>();
		            int updatedStopActor = 1;
		            while(updatedStopActor != 0) {
		            	Long actorId = Util.readLong("Digite o código do ator: ");
		            	updatedActorsIds.add(actorId);
		            	personRepository.addActorToMovie(actorId, updatedDto.getId()); // add no csv muitos para muitos
		            	stopActor = Util.readInt("Deseja adicionar mais algum ator? 1-Sim 0-Não: ");
		            	if(stopActor == 0) {
		            		break;
		            	}
		            }
		            updatedDto.setActorsIds(updatedActorsIds);
	            }
	            
	            movieController.update(updatedDto.getId(), updatedDto);
	            
	            // remover os atores e diretores não atualizados pelo usuário das tabelas de muitos para muitos
	            // obter ids de atores e diretores existentes no filme
	            List<Actor> existingActors = personRepository.findAllActorsByMovieId(updatedDto.getId());
	            List<Long> existingActorsIds = existingActors.stream().map(actor -> actor.getId()).collect(Collectors.toList());
	            
	            List<Director> existingDirectors = personRepository.findAllDirectorsByMovieId(updatedDto.getId());
	            List<Long> existingDirectorsIds = existingDirectors.stream().map(director -> director.getId()).collect(Collectors.toList());

	            // remover atores que não foram fornecidos pelo usuário
	            existingActorsIds.removeAll(updatedDto.getActorsIds());
	            personRepository.removeActorsFromMovie(existingActorsIds, updatedDto.getId());

	            // remover diretores que não foram fornecidos pelo usuário
	            existingDirectorsIds.removeAll(updatedDto.getDirectorsIds());
	            personRepository.removeDirectorsFromMovie(existingDirectorsIds, updatedDto.getId());
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um filme\n");
	            movieController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            movieController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Filmes...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}
	
	public static void showCinemas() {
		CinemaController cinemaController = new CinemaController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Cinemas");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os cinemas");
			System.out.println("2) Mostrar um cinema por código");
			System.out.println("3) Inserir novo cinema");
			System.out.println("4) Atualizar um cinema");
			System.out.println("5) Excluir um cinema");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os cinemas\n");
	            cinemaController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um cinema por código\n");
	            cinemaController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            cinemaController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo cinema\n");
	            CinemaDTO newDto = new CinemaDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setName(Util.readString("Digite o nome: "));
	            newDto.setAddress(Util.readString("Digite o endereço: "));
	            cinemaController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um cinema\n");
	            cinemaController.findAll();
	            CinemaDTO updatedDto = new CinemaDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setName(Util.readString("Digite o nome: "));
	            updatedDto.setAddress(Util.readString("Digite o endereço: "));
	            cinemaController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um cinema\n");
	            cinemaController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            cinemaController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Cinemas...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}
	
	public static void showRooms() {
		RoomController roomController = new RoomController();
		CinemaController cinemaController = new CinemaController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Salas");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todas as salas");
			System.out.println("2) Mostrar uma sala por código");
			System.out.println("3) Inserir nova sala");
			System.out.println("4) Atualizar uma sala");
			System.out.println("5) Excluir uma sala");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todas as salas\n");
	            roomController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar uma sala por código\n");
	            roomController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            roomController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir nova sala\n");
	            RoomDTO newDto = new RoomDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setNumber(Util.readInt("Digite o número da sala: "));
	            cinemaController.findAll();
	            newDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
	            roomController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar uma sala\n");
	            roomController.findAll();
	            RoomDTO updatedDto = new RoomDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setNumber(Util.readInt("Digite o número da sala: "));
	            cinemaController.findAll();
	            updatedDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
	            roomController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir uma sala\n");
	            roomController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            roomController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Salas...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}
	
	public static void showSchedules() {
		ScheduleController scheduleController = new ScheduleController();
		RoomController roomController = new RoomController();
		MovieController movieController = new MovieController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Horários");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os horários");
			System.out.println("2) Mostrar um horário por código");
			System.out.println("3) Inserir novo horário");
			System.out.println("4) Atualizar um horário");
			System.out.println("5) Excluir um horário");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os horários\n");
	            scheduleController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um horário por código\n");
	            scheduleController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            scheduleController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo horário\n");
	            ScheduleDTO newDto = new ScheduleDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setDate(Util.readDate("Informe uma data no formato dia/mês/ano: ")); 
	            newDto.setTime(Util.readTime("Informe um horário no formato hora:minuto: "));
	            movieController.findAll();
	            newDto.setMovieId(Util.readLong("Digite o id do filme: "));
	            roomController.findAll();
	            newDto.setRoomId(Util.readLong("Digite o número da sala: "));
	            scheduleController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um horário\n");
	            scheduleController.findAll();
	            ScheduleDTO updatedDto = new ScheduleDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setDate(Util.readDate("Informe uma data no formato dia/mês/ano: ")); 
	            updatedDto.setTime(Util.readTime("Informe um horário no formato hora:minuto: "));
	            movieController.findAll();
	            updatedDto.setMovieId(Util.readLong("Digite o id do filme: "));
	            roomController.findAll();
	            updatedDto.setRoomId(Util.readLong("Digite o número da sala: "));
	            scheduleController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um horário\n");
	            scheduleController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            scheduleController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Horários...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}
	
	public static void showSeats() {
		SeatController seatController = new SeatController();
		RoomController roomController = new RoomController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Assentos");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os assentos");
			System.out.println("2) Mostrar um assento por código");
			System.out.println("3) Inserir novo assento");
			System.out.println("4) Atualizar um assento");
			System.out.println("5) Excluir um assento");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os assentos\n");
	            seatController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um assento por código\n");
	            seatController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            seatController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo assento\n");
	            SeatDTO newDto = new SeatDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setNumber(Util.readInt("Digite o número: "));
	            newDto.setLine(Util.readInt("Digite a fileira: "));
	            roomController.findAll();
	            newDto.setRoomId(Util.readLong("Digite o código da sala: "));
	            seatController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um assento\n");
	            seatController.findAll();
	            SeatDTO updatedDto = new SeatDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setNumber(Util.readInt("Digite o número: "));
	            updatedDto.setLine(Util.readInt("Digite a fileira: "));
	            roomController.findAll();
	            updatedDto.setRoomId(Util.readLong("Digite o código da sala: "));
	            seatController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um assento\n");
	            seatController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            seatController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Assentos...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}
	
	public static void showTickets() {
		TicketController ticketController = new TicketController();
		SeatController seatController = new SeatController();
		MovieController movieController = new MovieController();
		ScheduleController scheduleController = new ScheduleController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Ingressos");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os ingressos");
			System.out.println("2) Mostrar um ingresso por código");
			System.out.println("3) Inserir novo ingresso");
			System.out.println("4) Atualizar um ingresso");
			System.out.println("5) Excluir um ingresso");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");
	
			switch (opc) {
			case 1:
	            System.out.println("Opção 1 selecionada: Mostrar todos os ingressos\n");
	            ticketController.findAll();
	            break;
	        case 2:
	            System.out.println("Opção 2 selecionada: Mostrar um ingresso por código\n");
	            ticketController.findAll();
	            Long id = Util.readLong("Digite o código: ");
	            ticketController.findById(id);
	            break;
	        case 3:
	            System.out.println("Opção 3 selecionada: Inserir novo ingresso\n");
	            TicketDTO newDto = new TicketDTO();
	            newDto.setId(Util.readLong("Digite o código: "));
	            newDto.setClientName(Util.readString("Digite o nome do cliente: "));
	            newDto.setPhoneNumber(Util.readString("Digite o número de telefone do clinte: "));
	            newDto.setPrice(Util.readDouble("Digite o preço: "));
	            newDto.setDate(Util.readDate("Digite a data no formato dia/mês/ano: "));
	            int half = Util.readInt("É meia entrada? 1-Sim 0-Não: ");
	            newDto.setHalfPrice(half == 1);
	            movieController.findAll();
	            newDto.setMovieId(Util.readLong("Digite o código do filme: "));
	            scheduleController.findAll();
	            newDto.setScheduleId(Util.readLong("Digite o código do horário: "));
	            seatController.findAll();
	            newDto.setSeatId(Util.readLong("Digite o código do assento: "));
	            ticketController.insert(newDto);
	            break;
	        case 4:
	            System.out.println("Opção 4 selecionada: Atualizar um ingresso\n");
	            ticketController.findAll();
	            TicketDTO updatedDto = new TicketDTO();
	            updatedDto.setId(Util.readLong("Digite o código: "));
	            updatedDto.setClientName(Util.readString("Digite o nome do cliente: "));
	            updatedDto.setPhoneNumber(Util.readString("Digite o número de telefone do clinte: "));
	            updatedDto.setPrice(Util.readDouble("Digite o preço: "));
	            updatedDto.setDate(Util.readDate("Digite a data no formato dia/mês/ano: "));
	            half = Util.readInt("É meia entrada? 1-Sim 0-Não: ");
	            updatedDto.setHalfPrice(half == 1);
	            movieController.findAll();
	            updatedDto.setMovieId(Util.readLong("Digite o código do filme: "));
	            scheduleController.findAll();
	            updatedDto.setScheduleId(Util.readLong("Digite o código do horário: "));
	            seatController.findAll();
	            updatedDto.setSeatId(Util.readLong("Digite o código do assento: "));
	            ticketController.update(updatedDto.getId(), updatedDto);
	            break;
	        case 5:
	            System.out.println("Opção 5 selecionada: Excluir um ingresso\n");
	            ticketController.findAll();
	            Long deletedId = Util.readLong("Digite o código: ");
	            ticketController.delete(deletedId);
	            break;
	        case 6:
	            System.out.println("Saindo de Ingressos...\n");
	            break;
	        default:
	            System.out.println("Opção inválida. Tente novamente.\n");
			}
		} while (opc != 6);
	}

	public static void main(String[] args) throws ParseException {
		
		showMenu();
		
		/* --------------- EXEMPLO DE IMPLEMENTAÇÃO DOS MÉTODOS --------------
		 * 
		 * 
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
