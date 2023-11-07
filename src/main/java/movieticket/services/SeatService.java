package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.SeatDTO;
import movieticket.entities.Seat;
import movieticket.entities.Ticket;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.IntegrityViolationException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.RoomRepository;
import movieticket.repositories.SeatRepository;
import movieticket.repositories.TicketRepository;

public class SeatService {

	private SeatRepository repository = new SeatRepository();
	private RoomRepository roomRepository = new RoomRepository();
	private TicketRepository ticketRepository = new TicketRepository();
	
	public List<SeatDTO> findAll(){
		List<Seat> list = repository.findAll();
		return list.stream().map(obj -> new SeatDTO(obj)).collect(Collectors.toList());
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
