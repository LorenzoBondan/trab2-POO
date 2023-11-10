package movieticket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import movieticket.dtos.SeatDTO;
import movieticket.entities.Schedule;
import movieticket.entities.Seat;
import movieticket.entities.Ticket;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.RoomRepository;
import movieticket.repositories.ScheduleRepository;
import movieticket.repositories.SeatRepository;
import movieticket.repositories.TicketRepository;

public class SeatService {

	private SeatRepository repository = new SeatRepository();
	private RoomRepository roomRepository = new RoomRepository();
	private TicketRepository ticketRepository = new TicketRepository();
	private ScheduleRepository scheduleRepository = new ScheduleRepository();
	
	public List<SeatDTO> findAll(){
		List<Seat> list = repository.findAll();
		return list.stream().map(obj -> new SeatDTO(obj)).collect(Collectors.toList());
	}

	public List<SeatDTO> findAvailableSeats(Long schedule_id, Long movie_id) {
		StringBuilder sb = new StringBuilder();
		List<Seat> freeSeats = new ArrayList<>();

		Schedule entity = scheduleRepository.findByIdAndMovieId(schedule_id, movie_id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		List<Seat> seats = repository.findAllByRoomId(entity.getRoom().getId());
		List<Ticket> tickets = ticketRepository.findAllByScheduleId(schedule_id);

		int fileiraAux = 0;
		int fileira = 0;

		sb.append("----------------\n");
		sb.append("|     TELA     |\n");
		sb.append("----------------");

		for(Seat seat : seats) {
			fileira = seat.getLine();
			if(fileiraAux != fileira) {
				sb.append("\nFileira " + fileira + ": ");
				fileiraAux = fileira;
			}
			boolean encontrou = false;

			for(Ticket ticket : tickets) {
				if (Objects.equals(seat.getId(), ticket.getSeat().getId())) {
					encontrou = true;
					break;
				}
			}

			if (encontrou) {
				sb.append(seat.getNumber() + "-X ");
			}else {
				freeSeats.add(seat);
				sb.append(seat.getNumber() + "-D ");
			}

		}

		System.out.println(sb.toString());

		if(freeSeats.isEmpty()) {
			System.out.println("\nNenhum assento livre!");
		}

		return freeSeats.stream().map(seat -> new SeatDTO(seat)).collect(Collectors.toList());
	}
	
	public SeatDTO findById(Long id) {
		Seat entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		List<Ticket> tickets = ticketRepository.findAllBySeatId(id);
		entity.setTickets(tickets);
		return new SeatDTO(entity);
	}
	
	public void insert(SeatDTO dto) {
		try {
			Seat entity = new Seat();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Assento inserido com sucesso: " + dto);
		} catch (DuplicateResourceException e) {
			throw new DuplicateResourceException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, SeatDTO dto) {
		try {
			Seat entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Assento não encontrado com o ID: " + id));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Assento atualizado com sucesso: " + dto);
		} catch (ResourceNotFoundException ex){
			throw new ResourceNotFoundException("Assento não encontrado com o ID: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		SeatDTO dto = findById(id);
		if(dto != null) {
			if(dto.getTicketsIds().isEmpty()){
				repository.delete(id);
				System.out.println("Assento deletado com sucesso: " + id);
			} else{
				throw new IntegrityViolationException("Não é possível deletar pois há dependências relacionadas a esse objeto");
			}
		} else{
			throw new ResourceNotFoundException("Assento não encontrado com o ID: " + id);
		}
	}
	
	private void copyDtoToEntity(SeatDTO dto, Seat entity) {
		entity.setId(dto.getId());
		entity.setNumber(dto.getNumber());
		entity.setLine(dto.getLine());
		entity.setRoom(roomRepository.findById(dto.getRoomId()).get());
		
		entity.getTickets().clear();
		for(Long ticketId : dto.getTicketsIds()) {
			Ticket ticket = ticketRepository.findById(ticketId).get();
			entity.getTickets().add(ticket);
		}
	}
}
