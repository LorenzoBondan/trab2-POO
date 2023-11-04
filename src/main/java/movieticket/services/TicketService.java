package movieticket.services;

import java.util.List;
import java.util.stream.Collectors;

import movieticket.dtos.TicketDTO;
import movieticket.entities.Ticket;
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
		Ticket entity = new Ticket();
		copyDtoToEntity(dto, entity);
		repository.insert(entity);
	}
	
	public void update(Long id, TicketDTO dto) {
		Ticket entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		copyDtoToEntity(dto, entity);
		repository.update(entity);
	}
	
	public void delete(Long id) {
		repository.delete(id);
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
