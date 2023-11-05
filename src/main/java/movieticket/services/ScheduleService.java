package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.ScheduleDTO;
import movieticket.entities.Schedule;
import movieticket.entities.Ticket;
import movieticket.exceptions.InvalidDataException;
import movieticket.exceptions.ResourceNotFoundException;
import movieticket.repositories.MovieRepository;
import movieticket.repositories.RoomRepository;
import movieticket.repositories.ScheduleRepository;
import movieticket.repositories.TicketRepository;

public class ScheduleService {

	private ScheduleRepository repository = new ScheduleRepository();
	private MovieRepository movieRepository = new MovieRepository();
	private RoomRepository roomRepository = new RoomRepository();
	private TicketRepository ticketRepository = new TicketRepository();
	
	public List<ScheduleDTO> findAll(){
		List<Schedule> list = repository.findAll();
		return list.stream().map(obj -> new ScheduleDTO(obj)).collect(Collectors.toList());
	}
	
	public ScheduleDTO findById(Long id) {
		Schedule entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new ScheduleDTO(entity);
	}
	
	public void insert(ScheduleDTO dto) {
		try {
			Schedule entity = new Schedule();
			copyDtoToEntity(dto, entity);
			repository.insert(entity);
			System.out.println("Horário inserido com sucesso.");
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
		
	}
	
	public void update(Long id, ScheduleDTO dto) {
		try {
			Schedule entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
			copyDtoToEntity(dto, entity);
			repository.update(entity);
			System.out.println("Horário atualizado com sucesso.");
		} catch(Exception e) {
			throw new InvalidDataException("Dados inválidos.");
		}
	}
	
	public void delete(Long id) {
		ScheduleDTO dto = findById(id);
		if(dto != null) {
			repository.delete(id);
			System.out.println("Horário deletado com sucesso: " + id);
		}
	}
	
	private void copyDtoToEntity(ScheduleDTO dto, Schedule entity) {
		entity.setId(dto.getId());
		entity.setDate(dto.getDate());
		entity.setTime(dto.getTime());
		entity.setRoom(roomRepository.findById(dto.getRoomId()).get());
		entity.setMovie(movieRepository.findById(dto.getMovieId()).get());
		
		entity.getTickets().clear();
		for(Long ticketId : dto.getTicketsIds()) {
			Ticket ticket = ticketRepository.findById(ticketId).get();
			entity.getTickets().add(ticket);
		}
	}
}
