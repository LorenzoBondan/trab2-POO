package movieticket.main;

import java.util.ArrayList;
import java.util.Date;
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
import movieticket.dtos.PersonDTO;
import movieticket.dtos.RoomDTO;
import movieticket.dtos.ScheduleDTO;
import movieticket.dtos.SeatDTO;
import movieticket.dtos.TicketDTO;
import movieticket.entities.Actor;
import movieticket.entities.Director;
import movieticket.entities.Seat;
import movieticket.repositories.PersonRepository;
import movieticket.util.Util;

public class Menu {
	
	public static void showMenu() {
		Scanner in = new Scanner(System.in);
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Bem-vindo ao MovieTicket Cinemas");
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
			System.out.println("10) Verificar Disponibilidade");
			System.out.println("11) Sair do sistema");
			System.out.println("------------------------------------------------------------");
			
			opc = Util.readInt("");

            switch (opc) {
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Gêneros");
					showGenres();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Filmes");
                    showMovies();
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Salas");
                    showRooms();
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Horários");
                    showSchedules();
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Assentos");
                    showSeats();
                }
                case 6 -> {
                    System.out.println("Opção 6 selecionada: Ingressos");
                    showTickets();
                }
                case 7 -> {
                    System.out.println("Opção 7 selecionada: Atores");
                    showActors();
                }
                case 8 -> {
                    System.out.println("Opção 8 selecionada: Diretores");
                    showDirectors();
                }
                case 9 -> {
                    System.out.println("Opção 9 selecionada: Cinemas");
                    showCinemas();
                }
                case 10 -> {
                    System.out.println("Opção 10 selecionada: Verificar Disponibilidade");
                    showDisponibilidade();
                }
                case 11 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
		} while (opc != 11);
		in.close();
	}
	
	public static void showGenres() {
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os gêneros\n");
                    genderController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um gênero por código\n");
                    genderController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    genderController.findById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo gênero\n");
                    GenderDTO newDto = new GenderDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setName(Util.readString("Digite o nome: "));
                    genderController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um gênero\n");
                    genderController.findAll();
                    GenderDTO updatedDto = new GenderDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setName(Util.readString("Digite o nome: "));
                    genderController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um gênero\n");
                    genderController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    genderController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Gêneros...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
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
			System.out.println("6) Mostrar filmes por nome");
			System.out.println("7) Mostrar filmes por período de datas");
			System.out.println("8) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");

            switch (opc) {
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os filmes\n");
                    movieController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um filme por código\n");
                    movieController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    movieController.findById(id);
                }
                case 3 -> {
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
                    int stopDirector;
                    do {
                        Long directorId = Util.readLong("Digite o código do diretor: ");
                        directorsIds.add(directorId);
                        personRepository.addDirectorToMovie(directorId, newDto.getId()); // add no csv muitos para muitos
                        stopDirector = Util.readInt("Deseja adicionar mais algum diretor? 1-Sim 0-Não: ");
                    } while (stopDirector != 0);
                    newDto.setDirectorsIds(directorsIds);
                    personController.findAllActors();
                    List<Long> actorsIds = new ArrayList<>();
                    int stopActor;
                    do {
                        Long actorId = Util.readLong("Digite o código do ator: ");
                        actorsIds.add(actorId);
                        personRepository.addActorToMovie(actorId, newDto.getId()); // add no csv muitos para muitos
                        stopActor = Util.readInt("Deseja adicionar mais algum ator? 1-Sim 0-Não: ");
                    } while (stopActor != 0);
                    newDto.setActorsIds(actorsIds);
                    movieController.insert(newDto);
                }
                case 4 -> {
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
                    if (question == 1) {
                        personController.findAllDirectors();
                        List<Long> updatedDirectorsIds = new ArrayList<>();
                        int updatedStopDirector;
                        do {
                            Long directorId = Util.readLong("Digite o código do diretor: ");
                            updatedDirectorsIds.add(directorId);
                            if (!personRepository.existsByDirectorIdAndMovieId(directorId, updatedDto.getId())) {
                                personRepository.addDirectorToMovie(directorId, updatedDto.getId()); // add no csv muitos para muitos
                            }
                            updatedStopDirector = Util.readInt("Deseja adicionar mais algum diretor? 1-Sim 0-Não: ");
                        } while (updatedStopDirector != 0);
                        updatedDto.setDirectorsIds(updatedDirectorsIds);
                    }
                    System.out.println("Atores já presentes nesse filme:");
                    personController.findAllActorsByMovieId(updatedDto.getId());
                    int question2 = Util.readInt("Deseja alterar os atores desse filme? 1-Sim 0-Não: ");
                    if (question2 == 1) {
                        personController.findAllActors();
                        List<Long> updatedActorsIds = new ArrayList<>();
                        int updatedStopActor;
                        do {
                            Long actorId = Util.readLong("Digite o código do ator: ");
                            updatedActorsIds.add(actorId);
                            if (!personRepository.existsByActorIdAndMovieId(actorId, updatedDto.getId())) {
                                personRepository.addActorToMovie(actorId, updatedDto.getId()); // add no csv muitos para muitos
                            }
                            updatedStopActor = Util.readInt("Deseja adicionar mais algum ator? 1-Sim 0-Não: ");
                        } while (updatedStopActor != 0);
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
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um filme\n");
                    movieController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    movieController.delete(deletedId);
                }
                case 6 -> {
                    System.out.println("Opção 6 selecionada: Mostrar filmes por nome\n");
                    String nome = Util.readString("Digite o nome: ");
                    movieController.findAllByName(nome);
                    int details = Util.readInt("Deseja ver os detalhes de algum filme? 1-Sim 0-Não: ");
                    if (details == 1) {
                        movieController.findById(Util.readLong("Digite o código do filme: "));
                    }
                }
                case 7 -> {
                    System.out.println("Opção 7 selecionada: Mostrar filmes por período de datas\n");
                    Date startDate = Util.readDate("Digite a data inicial no formato dia/mês/ano: ");
                    Date finalDate = Util.readDate("Digite a data final no formato dia/mês/ano: ");
                    movieController.findAllByDateRange(startDate, finalDate);
                }
                case 8 -> System.out.println("Saindo de Filmes...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
		} while (opc != 8);
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os cinemas\n");
                    cinemaController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um cinema por código\n");
                    cinemaController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    cinemaController.findById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo cinema\n");
                    CinemaDTO newDto = new CinemaDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setName(Util.readString("Digite o nome: "));
                    newDto.setAddress(Util.readString("Digite o endereço: "));
                    cinemaController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um cinema\n");
                    cinemaController.findAll();
                    CinemaDTO updatedDto = new CinemaDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setName(Util.readString("Digite o nome: "));
                    updatedDto.setAddress(Util.readString("Digite o endereço: "));
                    cinemaController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um cinema\n");
                    cinemaController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    cinemaController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Cinemas...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
		} while (opc != 6);
	}
	
	public static void showDisponibilidade() {
		MovieController movieController = new MovieController();
		ScheduleController scheduleController = new ScheduleController();
		TicketController ticketController = new TicketController();
		
		movieController.findAll();
		Long movieId = Util.readLong("Digite o código do filme que deseja assistir: ");
		scheduleController.findByMovieId(movieId);
		Long scheduleId = Util.readLong("Digite o código do horario que deseja ver: ");
		List<Seat> freeSeats = scheduleController.checkAvailableSeats(scheduleId, movieId);
			
		if(freeSeats == null || freeSeats.isEmpty()) {
			return;
		}
		
		System.out.println("\nEscolha uma das ações a seguir:");
		System.out.println("1) Escolher Assento");
		System.out.println("2) Sair");
		
		if (Util.readInt("") != 1) {
			return;
		}
		
		Seat chosenSeat = null;
		
		do {
			int fileira = Util.readInt("\nDigite a fileira do seu assento: ");
			int numero = Util.readInt("Digite o numero do seu assento: ");
						
			for(Seat seat : freeSeats) {
				if (seat.getLine() == fileira && seat.getNumber() == numero) {
					chosenSeat = seat;
				}
			}
			if(chosenSeat == null) {
				System.out.println("Este assento ja esta ocupado!");
			}
			
		}while(chosenSeat == null);		
		
		
        TicketDTO newDto = new TicketDTO();
        newDto.setId(Util.readLong("Digite o código: "));
        newDto.setClientName(Util.readString("Digite o nome do cliente: "));
        newDto.setPhoneNumber(Util.readString("Digite o número de telefone do cliente: "));
        newDto.setPrice(Util.readDouble("Digite o preço: "));
        newDto.setDate(Util.readDate("Digite a data no formato dia/mês/ano: "));
        int half = Util.readInt("É meia entrada? 1-Sim 0-Não: ");
        newDto.setHalfPrice(half == 1);
        newDto.setMovieId(movieId);
        newDto.setScheduleId(scheduleId);
        newDto.setSeatId(chosenSeat.getId());
        ticketController.insert(newDto);
        
        scheduleController.checkAvailableSeats(scheduleId, movieId);
		
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todas as salas\n");
                    roomController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar uma sala por código\n");
                    roomController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    roomController.findById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir nova sala\n");
                    RoomDTO newDto = new RoomDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setNumber(Util.readInt("Digite o número da sala: "));
                    cinemaController.findAll();
                    newDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
                    roomController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar uma sala\n");
                    roomController.findAll();
                    RoomDTO updatedDto = new RoomDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setNumber(Util.readInt("Digite o número da sala: "));
                    cinemaController.findAll();
                    updatedDto.setCinemaId(Util.readLong("Digite o código do cinema: "));
                    roomController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir uma sala\n");
                    roomController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    roomController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Salas...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os horários\n");
                    scheduleController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um horário por código\n");
                    scheduleController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    scheduleController.findById(id);
                }
                case 3 -> {
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
                }
                case 4 -> {
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
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um horário\n");
                    scheduleController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    scheduleController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Horários...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os assentos\n");
                    seatController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um assento por código\n");
                    seatController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    seatController.findById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo assento\n");
                    SeatDTO newDto = new SeatDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setNumber(Util.readInt("Digite o número: "));
                    newDto.setLine(Util.readInt("Digite a fileira: "));
                    roomController.findAll();
                    newDto.setRoomId(Util.readLong("Digite o código da sala: "));
                    seatController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um assento\n");
                    seatController.findAll();
                    SeatDTO updatedDto = new SeatDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setNumber(Util.readInt("Digite o número: "));
                    updatedDto.setLine(Util.readInt("Digite a fileira: "));
                    roomController.findAll();
                    updatedDto.setRoomId(Util.readLong("Digite o código da sala: "));
                    seatController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um assento\n");
                    seatController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    seatController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Assentos...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
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
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os ingressos\n");
                    ticketController.findAll();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um ingresso por código\n");
                    ticketController.findAll();
                    Long id = Util.readLong("Digite o código: ");
                    ticketController.findById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo ingresso\n");
                    TicketDTO newDto = new TicketDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setClientName(Util.readString("Digite o nome do cliente: "));
                    newDto.setPhoneNumber(Util.readString("Digite o número de telefone do cliente: "));
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
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um ingresso\n");
                    ticketController.findAll();
                    TicketDTO updatedDto = new TicketDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setClientName(Util.readString("Digite o nome do cliente: "));
                    updatedDto.setPhoneNumber(Util.readString("Digite o número de telefone do clinte: "));
                    updatedDto.setPrice(Util.readDouble("Digite o preço: "));
                    updatedDto.setDate(Util.readDate("Digite a data no formato dia/mês/ano: "));
                    int half2 = Util.readInt("É meia entrada? 1-Sim 0-Não: ");
                    updatedDto.setHalfPrice(half2 == 1);
                    movieController.findAll();
                    updatedDto.setMovieId(Util.readLong("Digite o código do filme: "));
                    scheduleController.findAll();
                    updatedDto.setScheduleId(Util.readLong("Digite o código do horário: "));
                    seatController.findAll();
                    updatedDto.setSeatId(Util.readLong("Digite o código do assento: "));
                    ticketController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um ingresso\n");
                    ticketController.findAll();
                    Long deletedId = Util.readLong("Digite o código: ");
                    ticketController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Ingressos...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
		} while (opc != 6);
	}
	
	public static void showActors() {
		PersonController personController = new PersonController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Atores");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os atores");
			System.out.println("2) Mostrar um ator por código");
			System.out.println("3) Inserir novo ator");
			System.out.println("4) Atualizar um ator");
			System.out.println("5) Excluir um ator");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");

            switch (opc) {
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os atores\n");
                    personController.findAllActors();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um ator por código\n");
                    personController.findAllActors();
                    Long id = Util.readLong("Digite o código: ");
                    personController.findActorById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo ator\n");
                    PersonDTO newDto = new PersonDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setName(Util.readString("Digite o nome: "));
                    newDto.setRole("Actor");
                    personController.findAll();
                    newDto.setMarriedId(Util.readLong("Digite o código do cônjuge, se não houver, digite zero: "));
                    if (newDto.getMarriedId() == 0) {
                        newDto.setMarriedId(null);
                    }
                    personController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um ator\n");
                    personController.findAllActors();
                    PersonDTO updatedDto = new PersonDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setName(Util.readString("Digite o nome: "));
                    updatedDto.setRole("Actor");
                    personController.findAll();
                    updatedDto.setMarriedId(Util.readLong("Digite o código do cônjuge, se não houver, digite zero: "));
                    if (updatedDto.getMarriedId() == 0) {
                        updatedDto.setMarriedId(null);
                    }
                    personController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um ator\n");
                    personController.findAllActors();
                    Long deletedId = Util.readLong("Digite o código: ");
                    personController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Atores...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
		} while (opc != 6);
	}
	
	public static void showDirectors() {
		PersonController personController = new PersonController();
		
		int opc;
		do {
			System.out.println("------------------------------------------------------------");
			System.out.println("Diretores");
			System.out.println("------------------------------------------------------------");
			System.out.println("Escolha uma das ações a seguir:");
			System.out.println("1) Mostrar todos os diretores");
			System.out.println("2) Mostrar um diretor por código");
			System.out.println("3) Inserir novo diretor");
			System.out.println("4) Atualizar um diretor");
			System.out.println("5) Excluir um diretor");
			System.out.println("6) Sair");
			System.out.println("------------------------------------------------------------");

			opc = Util.readInt("");

            switch (opc) {
                case 1 -> {
                    System.out.println("Opção 1 selecionada: Mostrar todos os diretores\n");
                    personController.findAllDirectors();
                }
                case 2 -> {
                    System.out.println("Opção 2 selecionada: Mostrar um diretor por código\n");
                    personController.findAllDirectors();
                    Long id = Util.readLong("Digite o código: ");
                    personController.findDirectorById(id);
                }
                case 3 -> {
                    System.out.println("Opção 3 selecionada: Inserir novo diretor\n");
                    PersonDTO newDto = new PersonDTO();
                    newDto.setId(Util.readLong("Digite o código: "));
                    newDto.setName(Util.readString("Digite o nome: "));
                    newDto.setRole("Director");
                    personController.findAll();
                    newDto.setMarriedId(Util.readLong("Digite o código do cônjuge, se não houver, digite zero: "));
                    if (newDto.getMarriedId() == 0) {
                        newDto.setMarriedId(null);
                    }
                    personController.insert(newDto);
                }
                case 4 -> {
                    System.out.println("Opção 4 selecionada: Atualizar um diretor\n");
                    personController.findAllDirectors();
                    PersonDTO updatedDto = new PersonDTO();
                    updatedDto.setId(Util.readLong("Digite o código: "));
                    updatedDto.setName(Util.readString("Digite o nome: "));
                    updatedDto.setRole("Director");
                    personController.findAll();
                    updatedDto.setMarriedId(Util.readLong("Digite o código do cônjuge, se não houver, digite zero: "));
                    if (updatedDto.getMarriedId() == 0) {
                        updatedDto.setMarriedId(null);
                    }
                    personController.update(updatedDto.getId(), updatedDto);
                }
                case 5 -> {
                    System.out.println("Opção 5 selecionada: Excluir um diretor\n");
                    personController.findAllDirectors();
                    Long deletedId = Util.readLong("Digite o código: ");
                    personController.delete(deletedId);
                }
                case 6 -> System.out.println("Saindo de Diretores...\n");
                default -> System.out.println("Opção inválida. Tente novamente.\n");
            }
		} while (opc != 6);
	}

	public static void main(String[] args) {
		showMenu();
	}
	
}
