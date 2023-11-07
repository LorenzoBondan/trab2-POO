package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.TicketDTO;
import movieticket.entities.Ticket;
import movieticket.exceptions.DuplicateResourceException;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.MovieRepository;
import movieticket.repositories.ScheduleRepository;
import movieticket.repositories.SeatRepository;
import movieticket.repositories.TicketRepository;

public class TicketService {

	private TicketRepository repository = new TicketRepository();
	private MovieRepository movieRepository = new MovieRepository();
	private ScheduleRepository scheduleRepository = new ScheduleRepository();
	private SeatRepository seatRepository = new SeatRepository();
	
	public List<TicketDTO> findAll(){
		List<Ticket> list = repository.findAll();
		return list.stream().map(obj -> new TicketDTO(obj)).collect(Collectors.toList());
	}
	
	public TicketDTO findById(Long id) {
		Ticket entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new TicketDTO(entity);
	}
	
	public void insert(TicketDTO dto) {
		try {
			Ticket entity = new Ticket();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Ingresso inserido com sucesso: " + dto);
		} catch (DuplicateResourceException e) {
			throw new DuplicateResourceException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void update(Long id, TicketDTO dto) {
		try {
			Ticket entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingresso não encontrado com o ID: " + id));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Ingresso atualizado com sucesso: " + dto);
		} catch (ResourceNotFoundException ex){
			throw new ResourceNotFoundException("Ingresso não encontrado com o ID: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		TicketDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Ingresso deletado com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(TicketDTO dto, Ticket entity) {
		entity.setId(dto.getId());
		entity.setClientName(dto.getClientName());
		entity.setDate(dto.getDate());
		entity.setHalfPrice(dto.getHalfPrice());
		entity.setPhoneNumber(dto.getPhoneNumber());
		entity.setPrice(dto.getPrice());
		entity.setSchedule(scheduleRepository.findById(dto.getScheduleId()).get());
		entity.setSeat(seatRepository.findById(dto.getSeatId()).get());
		entity.setMovie(movieRepository.findById(dto.getMovieId()).get());
	}
}
